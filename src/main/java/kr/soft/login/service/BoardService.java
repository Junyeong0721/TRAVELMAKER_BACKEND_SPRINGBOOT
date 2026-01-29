package kr.soft.login.service;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
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

    // 게시글 목록 조회
    public List<BoardListDTO> list(int offset) {
        return boardMapper.list(offset);
    }

    // 게시글 상세 조회
    public BoardDetailResponse detail(Long idx, Long userIdx){
        // 조회수 증가
        boardMapper.plusViewCount(idx);

        BoardDetailResponse response = new BoardDetailResponse();

        // 내 글인지 확인
        long resultMine = boardMapper.mine(idx);
        response.setMine(resultMine == userIdx);

        // 상세 정보 세팅
        response.setPost(boardMapper.detail(idx));
        response.setRoadmap(boardMapper.roadmap(idx));
        response.setComments(boardMapper.comment(idx));

        // [수정됨] 오타 수정 (>q 0 -> > 0)
        // 좋아요 여부 체크 (userIdx가 0이면 당연히 0이 나와서 false가 됨)
        response.setCheckedLike(likeMapper.checkLike(idx, userIdx) > 0);

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

    // [추가됨] 베스트 게시글 목록 (Controller에 있는데 누락되어 있어서 추가)
    public List<BoardListDTO> bestlist(int offset) {
        return boardMapper.bestlist(offset);
    }


    public List<BoardListDTO> search(String keyword, int offset) {
        // 매퍼에 파라미터 두 개를 그대로 전달
        return boardMapper.search(keyword, offset);
    }

    public int getSearchCount(String keyword) {
        return boardMapper.searchCount(keyword);
    }


    // [추가됨] 내가 쓴 글 목록 (Controller에 있는데 누락되어 있어서 추가)
    public List<BoardListDTO> mylist(BoardMyListParam param) {
        return boardMapper.mylist(param);
    }
}

