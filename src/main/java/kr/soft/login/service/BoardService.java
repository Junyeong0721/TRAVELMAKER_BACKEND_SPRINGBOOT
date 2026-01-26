package kr.soft.login.service;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
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

    public List<BoardListDTO> list(int offset) {
        List<BoardListDTO> lists = boardMapper.list(offset);

        return lists;
    }
    public BoardDetailResponse detail(Long idx, Long userIdx){

        boardMapper.plusViewCount(idx);

        BoardDetailResponse response = new BoardDetailResponse();

        long resultMine = boardMapper.mine(idx);

        if(resultMine == userIdx){
            response.setMine(true);
        }
        else
            response.setMine(false);

        response.setPost(boardMapper.detail(idx));
        response.setRoadmap(boardMapper.roadmap(idx));
        response.setComments(boardMapper.comment(idx));
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
}
