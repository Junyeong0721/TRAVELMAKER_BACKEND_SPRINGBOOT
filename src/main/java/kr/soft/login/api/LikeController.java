package kr.soft.login.api;

import kr.soft.login.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    LikeService likeService;

    @GetMapping("/check")
    public ResponseEntity<?> checkLike(@RequestParam("postIdx") Long postIdx,
                                        @RequestAttribute("userIdx") Long userIdx) {
            // Service에서 좋아요 추가/삭제 로직 처리
        boolean isChecked = likeService.checkLike(postIdx, userIdx);
        log.info("@@@@@@@@@@@@@@@@@@@@@check like isChecked={}", isChecked);
        return ResponseEntity.ok(isChecked); // true면 좋아요 됨, false면 취소됨
    }
    @GetMapping("/add")
    public ResponseEntity<?> insertLike(@RequestParam("postIdx") Long postIdx,
                                        @RequestAttribute("userIdx") Long userIdx){
        boolean isChecked = likeService.checkLike(postIdx, userIdx);
        // ✅ isChecked가 false(없을 때)여야 추가를 합니다.
        if(!isChecked) {
            likeService.addLike(postIdx, userIdx);
            log.info("@@@@@@@@@@@@@@@@@@@@@좋아요 추가 실행");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteLike(@RequestParam("postIdx") Long postIdx,
                                        @RequestAttribute("userIdx") Long userIdx){
        boolean isChecked = likeService.checkLike(postIdx, userIdx);
        // ✅ isChecked가 true(이미 있을 때)여야 삭제를 합니다.
        if(isChecked) {
            likeService.removeLike(postIdx, userIdx);
            log.info("@@@@@@@@@@@@@@@@@@@@@좋아요 삭제 실행");
        }
        return ResponseEntity.ok().build();
    }
}
