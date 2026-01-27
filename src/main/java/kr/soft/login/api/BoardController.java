package kr.soft.login.api;

import kr.soft.login.config.jwt.JwtTokenProvider;
import kr.soft.login.dto.Board.*;
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
    public ResponseEntity<BoardDetailResponse> detail(@RequestParam("idx") Long idx, @RequestAttribute("userIdx") long userIdx) {

        BoardDetailResponse detail = boardService.detail(idx, userIdx);



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
    @GetMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam("idx") Long idx){

        BoardEditDTO boardEditDTO = boardService.edit(idx);

        return  ResponseEntity.ok(boardEditDTO);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody BoardUpdateDTO boardUpdateDTO){
        boardService.update(boardUpdateDTO);
        log.info("update = {}", boardUpdateDTO.toString());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("idx") Long idx){

        boardService.delete(idx);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/top3")
    public ResponseEntity<List<BoardTop3DTO>> top3(){

        List<BoardTop3DTO> toplist = boardService.top3();

        return ResponseEntity.ok(toplist);
    }

    @GetMapping("/detail/{idx}")
    public ResponseEntity<BoardDetailResponse> detail(
            @PathVariable Long idx,
            // [추가] 토큰에서 내 ID 꺼내기 (로그인 안 했으면 null 일 수도 있음)
            @RequestAttribute(value = "userIdx", required = false) Long myIdx
    ) {
        // 서비스에 내 번호(myIdx)도 같이 넘김
        return ResponseEntity.ok(boardService.detail(idx, myIdx));
    }

}
