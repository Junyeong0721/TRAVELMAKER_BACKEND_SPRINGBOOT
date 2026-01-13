package kr.soft.login.api;

import kr.soft.login.dto.Board.BoardDetailDTO;
import kr.soft.login.dto.Board.BoardListDTO;
import kr.soft.login.mapper.BoardMapper;
import kr.soft.login.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//수정

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<List<BoardListDTO>> list() {
        log.info("board list success");

        List<BoardListDTO> lists = boardService.list();

        return ResponseEntity.ok(lists);



    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDetailDTO> detail(@RequestParam("idx") Long idx) {
        log.info("board detail success");

        BoardDetailDTO detail = boardService.detail(idx);

        return ResponseEntity.ok(detail);

    }
}
