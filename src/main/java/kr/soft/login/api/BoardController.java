package kr.soft.login.api;

import kr.soft.login.config.jwt.JwtTokenProvider;
import kr.soft.login.dto.Board.BoardDetailDTO;
import kr.soft.login.dto.Board.BoardDetailResponse;
import kr.soft.login.dto.Board.BoardListDTO;
import kr.soft.login.dto.Board.BoardWriteDTO;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.mapper.BoardMapper;
import kr.soft.login.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//수정

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/list")
    public ResponseEntity<List<BoardListDTO>> list(@RequestParam(defaultValue = "0") int offset) {

        List<BoardListDTO> boardlist = boardService.list(offset);


        return ResponseEntity.ok(boardlist);
    }

    @GetMapping("/detail")
    public ResponseEntity<BoardDetailResponse> detail(@RequestParam("idx") Long idx) {

        BoardDetailResponse detail = boardService.detail(idx);

        return ResponseEntity.ok(detail);

    }

    @PostMapping("/write")
    public ResponseEntity<Void> write(@RequestBody BoardWriteDTO boardWriteDTO,
                                      @RequestAttribute("userIdx") long idx) {

        boardWriteDTO.setUserIdx(idx);


        boardService.write(boardWriteDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalCount() {
        int count = boardService.getTotalCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> comment(@ModelAttribute CommentReq req,
                                        @RequestAttribute("userIdx") long userIdx){
        req.setUserIdx(userIdx);
        boardService.insertcomment(req);


        return ResponseEntity.ok().build();
    }
    @GetMapping("/selectplan")
    public ResponseEntity<List<SelectPlanDTO>> selectplan(@RequestAttribute("userIdx") long userIdx){
        List<SelectPlanDTO> planResponse = boardService.selectplan(userIdx);
        return ResponseEntity.ok(planResponse);
    }

}
