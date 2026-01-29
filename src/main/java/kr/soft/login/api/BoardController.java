package kr.soft.login.api;

import kr.soft.login.config.jwt.JwtTokenProvider;
import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.plan.SelectPlanDTO;
import kr.soft.login.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardListDTO>> list(@RequestParam(defaultValue = "0") int offset) {
        List<BoardListDTO> boardlist = boardService.list(offset);
        return ResponseEntity.ok(boardlist);
    }

    // [수정됨] 게시글 상세 조회 (기존 2개였던 메서드를 하나로 통합 + 비로그인 예외처리)
    // 주소 예시: /api/board/detail/5
    @GetMapping("/detail")
    public ResponseEntity<BoardDetailResponse> detail(
            @RequestParam("idx") Long idx, // @PathVariable 대신 @RequestParam 사용
            // 로그인을 안 했으면 userIdx가 없을 수 있으므로 required = false
            @RequestAttribute(value = "userIdx", required = false) Long userIdx
    ) {
        // userIdx가 null(비로그인)이면 0으로 변환해서 서비스에 넘김
        long safeUserIdx = (userIdx != null) ? userIdx : 0L;

        return ResponseEntity.ok(boardService.detail(idx, safeUserIdx));
    }

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Void> write(@RequestBody BoardWriteDTO boardWriteDTO,
                                      @RequestAttribute("userIdx") long idx) {
        boardWriteDTO.setUserIdx(idx);
        boardService.write(boardWriteDTO);
        return ResponseEntity.ok().build();
    }

    // 전체 게시글 수 조회
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalCount() {
        int count = boardService.getTotalCount();
        return ResponseEntity.ok(count);
    }

    // 댓글 작성
    @GetMapping("/comment")
    public ResponseEntity<?> comment(@ModelAttribute CommentReq req,
                                     @RequestAttribute("userIdx") long userIdx){
        req.setUserIdx(userIdx);
        boardService.insertcomment(req);
        return ResponseEntity.ok().build();
    }

    // 여행 계획 선택 (글 쓸 때 가져오기)
    @GetMapping("/selectplan")
    public ResponseEntity<List<SelectPlanDTO>> selectplan(@RequestAttribute("userIdx") long userIdx){
        List<SelectPlanDTO> planResponse = boardService.selectplan(userIdx);
        return ResponseEntity.ok(planResponse);
    }

    // 수정할 때 기존 데이터 가져오기
    @GetMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam("idx") Long idx){
        BoardEditDTO boardEditDTO = boardService.edit(idx);
        return ResponseEntity.ok(boardEditDTO);
    }

    // 게시글 수정 업데이트
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody BoardUpdateDTO boardUpdateDTO){
        boardService.update(boardUpdateDTO);
        log.info("update = {}", boardUpdateDTO.toString());
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("idx") Long idx){
        boardService.delete(idx);
        return ResponseEntity.ok().build();
    }

    // 인기글 TOP 3
    @GetMapping("/top3")
    public ResponseEntity<List<BoardTop3DTO>> top3(){
        List<BoardTop3DTO> toplist = boardService.top3();
        return ResponseEntity.ok(toplist);
    }

    // 베스트 게시글 목록
    @GetMapping("/bestlist")
    public ResponseEntity<List<BoardListDTO>> bestlist(@RequestParam(defaultValue = "0") int offset) {
        List<BoardListDTO> boardlist = boardService.bestlist(offset);
        return ResponseEntity.ok(boardlist);
    }

    // 내가 쓴 글 목록
    @GetMapping("/mylist")
    public ResponseEntity<List<BoardListDTO>> mylist(@RequestParam(defaultValue = "0") int offset, @RequestAttribute("userIdx") long userIdx) {
        BoardMyListParam boardMyListParam = new BoardMyListParam();
        boardMyListParam.setOffset(offset);
        boardMyListParam.setUserIdx(userIdx);
        List<BoardListDTO> boardlist = boardService.mylist(boardMyListParam);
        return ResponseEntity.ok(boardlist);
    }
}