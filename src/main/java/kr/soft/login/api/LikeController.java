package kr.soft.login.api;

import jakarta.servlet.http.HttpServletRequest;
import kr.soft.login.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/likes") // ★ [수정 1] 주소를 'likes' (복수형)으로 맞춤
public class LikeController {

    @Autowired
    LikeService likeService;

    // ★ [수정 2] POST 방식의 토글(Toggle) 메서드 하나로 통합
    @PostMapping("/{postIdx}")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long postIdx,
            HttpServletRequest request // Interceptor가 넣어준 userIdx를 꺼내기 위함
    ) {
        // 1. 로그인 여부 확인 (Interceptor에서 userIdx를 setAttribute 했다고 가정)
        Object userIdxObj = request.getAttribute("userIdx");
        if (userIdxObj == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }
        Long userIdx = Long.parseLong(userIdxObj.toString());

        // 2. 현재 좋아요 상태 확인
        boolean isLiked = likeService.checkLike(postIdx, userIdx);

        // 3. 상태에 따라 추가 또는 삭제 (토글 로직)
        if (isLiked) {
            // 이미 좋아요 상태라면 -> 삭제
            likeService.removeLike(postIdx, userIdx);
            log.info("좋아요 취소 실행: postIdx={}, userIdx={}", postIdx, userIdx);
            return ResponseEntity.ok(false); // 결과: 안 좋아요 상태 (false)
        } else {
            // 좋아요가 없다면 -> 추가
            likeService.addLike(postIdx, userIdx);
            log.info("좋아요 추가 실행: postIdx={}, userIdx={}", postIdx, userIdx);
            return ResponseEntity.ok(true); // 결과: 좋아요 상태 (true)
        }
    }
}