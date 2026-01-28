package kr.soft.login.api;

import kr.soft.login.service.OtherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/other")
public class OtherController {

    @Autowired
    private OtherService otherService;

    // 타 유저 프로필 + 게시글 조회 (페이징 기능 추가됨)
    @GetMapping("/{targetUserIdx}")
    public ResponseEntity<Map<String, Object>> getOtherUserProfile(
            @PathVariable Long targetUserIdx,
            // 인터셉터에서 추출한 내 ID (비로그인 시 null)
            @RequestAttribute(value = "userIdx", required = false) Long myUserIdx,
            // [추가] 프론트엔드에서 넘겨주는 페이지 번호 (안 보내면 기본 1)
            @RequestParam(defaultValue = "1") int page
    ) {

        // 비로그인 상태일 경우 myUserIdx 처리
        if (myUserIdx == null) {
            myUserIdx = 0L;
        }

        log.info("타 유저 프로필 조회 - 타겟: {}, 조회자: {}, 페이지: {}", targetUserIdx, myUserIdx, page);

        // 서비스 호출 (targetId, myId, page 3개를 넘김)
        Map<String, Object> result = otherService.getOtherPageData(targetUserIdx, myUserIdx, page);

        // 프로필 정보가 없으면(유저가 없으면) 404 리턴
        if (result.get("profile") == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}