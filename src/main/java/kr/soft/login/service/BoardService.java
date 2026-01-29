package kr.soft.login.service;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
import kr.soft.login.mapper.LikeMapper;
import kr.soft.login.mapper.PlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class BoardService {

    @Autowired
    private  BoardMapper boardMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private LikeMapper likeMapper;

    public List<BoardListDTO> list(int offset) {
        List<BoardListDTO> lists = boardMapper.list(offset);

        return lists;
    }
    public BoardDetailResponse detail(Long idx, Long userIdx){

        boardMapper.plusViewCount(idx);

        BoardDetailResponse response = new BoardDetailResponse();

        long resultMine = boardMapper.mine(idx);

        response.setMine(resultMine == userIdx);

        response.setPost(boardMapper.detail(idx));
        response.setRoadmap(boardMapper.roadmap(idx));
        response.setComments(boardMapper.comment(idx));
        response.setCheckedLike(likeMapper.checkLike(idx, userIdx) > 0);
        return response;


    }
    public void write(BoardWriteDTO boardWriteDTO) {

        boardMapper.write(boardWriteDTO);

    }
    public int getTotalCount(){

        return boardMapper.count();
    }
    public void insertcomment(CommentReq commentReq){
        boardMapper.insertcomment(commentReq);
    }
    public List<SelectPlanDTO> selectplan(Long userIdx){
        List<SelectPlanDTO> resultDTO = planMapper.selectplan(userIdx);
        return resultDTO;
    }
    public BoardEditDTO edit(Long postIdx){
        BoardEditDTO editDTO = boardMapper.edit(postIdx);
        return editDTO;
    }
    public void update(BoardUpdateDTO boardUpdateDTO){
        boardMapper.updateBoard(boardUpdateDTO);
    }
    public void delete(Long idx){
        log.info("@@@@@@@@@@delete@@@@@@@@@");
        boardMapper.deletePost(idx);
    }
    public List<BoardTop3DTO> top3(){
        List<BoardTop3DTO> toplist = boardMapper.top3();
        return toplist;
    }
    public List<BoardListDTO> bestlist(int offset) {
        List<BoardListDTO> lists = boardMapper.bestlist(offset);

        return lists;
    }
    public List<BoardListDTO> mylist(BoardMyListParam boardMyListParam) {
        List<BoardListDTO> lists = boardMapper.mylist(boardMyListParam);

        return lists;
    }

    public List<BoardListDTO> search(String keyword, int offset) {
        // 매퍼에 파라미터 두 개를 그대로 전달
        return boardMapper.search(keyword, offset);
    }

    public int getSearchCount(String keyword) {
        return boardMapper.searchCount(keyword);
    }
}
