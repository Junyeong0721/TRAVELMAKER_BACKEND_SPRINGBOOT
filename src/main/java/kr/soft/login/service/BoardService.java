package kr.soft.login.service;

import kr.soft.login.dto.Board.BoardDetailDTO;
import kr.soft.login.dto.Board.BoardListDTO;
import kr.soft.login.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class BoardService {

    @Autowired
    private  BoardMapper boardMapper;

    public List<BoardListDTO> list() {
        List<BoardListDTO> lists = boardMapper.list();
        return lists;
    }
    public BoardDetailDTO detail(Long idx){
        BoardDetailDTO detail = boardMapper.detail(idx);
        return detail;
    }
}
