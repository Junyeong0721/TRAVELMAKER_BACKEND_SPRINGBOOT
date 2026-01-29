package kr.soft.login.service;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
import kr.soft.login.mapper.FollowMapper; // ★ 추가
import kr.soft.login.mapper.LikeMapper;
import kr.soft.login.mapper.PlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map; // ★ 추가

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private FollowMapper followMapper; // ★ [추가] 팔로우 확인용

    // 게시글 목록 조회
    public List<BoardListDTO> list(int offset) {
        return boardMapper.list(offset);
    }

    // 게시글 상세 조회
    public BoardDetailResponse detail(Long idx, Long userIdx){
        // 1. 조회수 증가
        boardMapper.plusViewCount(idx);

        BoardDetailResponse response = new BoardDetailResponse();

        // 2. 내 글인지 확인
        long resultMine = boardMapper.mine(idx);
        response.setMine(resultMine == userIdx);

        // 3. 게시글 상세 정보 가져오기 (idx, userIdx 넘김)
        BoardDetailDTO post = boardMapper.detail(idx, userIdx);
        response.setPost(post);

        // 4. 로드맵 & 댓글 가져오기
        response.setRoadmap(boardMapper.roadmap(idx));
        response.setComments(boardMapper.comment(idx));

        // 5. 좋아요 여부 체크 (Response Root에 세팅)
        // checkLike 결과가 0보다 크면 true
        response.setCheckedLike(likeMapper.checkLike(idx, userIdx) > 0);

        // ★★★ 6. [핵심 추가] 팔로우 여부 체크 ★★★
        // 로그인했고(userIdx != 0), 게시글 정보가 있을 때만 실행
        if (userIdx != 0 && post != null) {
            Long authorIdx = post.getUserIdx(); // 게시글 작성자 ID (DTO에 이 필드가 있어야 함)

            // 작성자가 존재하고, 내가 작성자가 아닐 때만 확인
            if (authorIdx != null && !authorIdx.equals(userIdx)) {
                // FollowMapper를 통해 DB 조회 (나 -> 작성자)
                Map<String, Object> followInfo = followMapper.findByFollowerAndFollowing(userIdx, authorIdx);

                // 데이터가 존재하고, 삭제 여부(DELETE_YN)가 'N'이면 팔로우 중
                if (followInfo != null && "N".equals(followInfo.get("DELETE_YN"))) {
                    response.setCheckedFollow(true);
                } else {
                    response.setCheckedFollow(false);
                }
            }
        }

        return response;
    }

    // 글 쓰기
    public void write(BoardWriteDTO boardWriteDTO) {
        boardMapper.write(boardWriteDTO);
    }

    // 전체 글 개수
    public int getTotalCount(){
        return boardMapper.count();
    }

    // 댓글 작성
    public void insertcomment(CommentReq commentReq){
        boardMapper.insertcomment(commentReq);
    }

    // 여행 계획 불러오기
    public List<SelectPlanDTO> selectplan(Long userIdx){
        return planMapper.selectplan(userIdx);
    }

    // 수정할 글 가져오기
    public BoardEditDTO edit(Long postIdx){
        return boardMapper.edit(postIdx);
    }

    // 글 수정 실행
    public void update(BoardUpdateDTO boardUpdateDTO){
        boardMapper.updateBoard(boardUpdateDTO);
    }

    // 글 삭제
    public void delete(Long idx){
        log.info("@@@@@@@@@@delete@@@@@@@@@");
        boardMapper.deletePost(idx);
    }

    // 인기글 Top3
    public List<BoardTop3DTO> top3(){
        return boardMapper.top3();
    }

    // 베스트 게시글 목록
    public List<BoardListDTO> bestlist(int offset) {
        return boardMapper.bestlist(offset);
    }

    // 내가 쓴 글 목록
    public List<BoardListDTO> mylist(BoardMyListParam param) {
        return boardMapper.mylist(param);
    }
}