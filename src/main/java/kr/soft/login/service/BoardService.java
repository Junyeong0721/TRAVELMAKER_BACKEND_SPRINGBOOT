package kr.soft.login.service;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
import kr.soft.login.mapper.FollowMapper; // ✅ [추가] FollowMapper 임포트
import kr.soft.login.mapper.LikeMapper;
import kr.soft.login.mapper.PlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private FollowMapper followMapper; // ✅ [추가] 팔로우 확인용 매퍼 주입

    public List<BoardListDTO> list(int offset) {
        List<BoardListDTO> lists = boardMapper.list(offset);
        return lists;
    }

    public BoardDetailResponse detail(Long idx, Long userIdx) {
        // 1. 조회수 증가
        boardMapper.plusViewCount(idx);

        BoardDetailResponse response = new BoardDetailResponse();

        // 2. 내 글인지 확인
        // (주의: boardMapper.mine(idx)가 null을 반환할 수도 있으니 예외처리 필요할 수 있음)
        try {
            long writerIdx = boardMapper.mine(idx);
            // userIdx가 null(비로그인)이면 false
            response.setMine(userIdx != null && writerIdx == userIdx);
        } catch (Exception e) {
            response.setMine(false);
        }

        // 3. 게시글 상세 정보 가져오기
        BoardDetailDTO postDetail = boardMapper.detail(idx);

        // ✅ [추가된 로직] 팔로우 여부 체크
        // 로그인 상태(userIdx != null)이고, 게시글 작성자 정보가 있을 때만 실행
        if (userIdx != null && postDetail != null && postDetail.getUserIdx() != null) {

            // 내가(userIdx) 이 글 작성자(postDetail.getUserIdx())를 팔로우했는지 확인
            int followCount = followMapper.checkFollow(userIdx, postDetail.getUserIdx());

            if (followCount > 0) {
                postDetail.setFollowed(true); // 팔로우 중이면 true로 설정
            }
        }

        // 4. Response 객체에 데이터 담기
        response.setPost(postDetail); // 수정된(isFollowed가 세팅된) DTO 넣기
        response.setRoadmap(boardMapper.roadmap(idx));
        response.setComments(boardMapper.comment(idx));

        // 좋아요 여부 체크
        if (userIdx != null) {
            response.setCheckedLike(likeMapper.checkLike(idx, userIdx) > 0);
        } else {
            response.setCheckedLike(false);
        }

        return response;
    }

    public void write(BoardWriteDTO boardWriteDTO) {
        boardMapper.write(boardWriteDTO);
    }

    public int getTotalCount() {
        return boardMapper.count();
    }

    public void insertcomment(CommentReq commentReq) {
        boardMapper.insertcomment(commentReq);
    }

    public List<SelectPlanDTO> selectplan(Long userIdx) {
        List<SelectPlanDTO> resultDTO = planMapper.selectplan(userIdx);
        return resultDTO;
    }

    public BoardEditDTO edit(Long postIdx) {
        BoardEditDTO editDTO = boardMapper.edit(postIdx);
        return editDTO;
    }

    public void update(BoardUpdateDTO boardUpdateDTO) {
        boardMapper.updateBoard(boardUpdateDTO);
    }

    public void delete(Long idx) {
        log.info("@@@@@@@@@@delete@@@@@@@@@");
        boardMapper.deletePost(idx);
    }

    public List<BoardTop3DTO> top3() {
        List<BoardTop3DTO> toplist = boardMapper.top3();
        return toplist;
    }
}