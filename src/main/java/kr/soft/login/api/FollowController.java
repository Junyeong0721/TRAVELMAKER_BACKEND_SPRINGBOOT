package kr.soft.login.api;

import kr.soft.login.dto.follow.FollowDTO;
import kr.soft.login.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    // Service 주입 (이 부분이 있어야 아래 메서드들이 작동합니다)
    private final FollowService followService;

    // 버튼 누를 때마다 실행 (팔로우 <-> 언팔로우)
    @PostMapping("/{targetIdx}")
    public ResponseEntity<String> toggleFollow(
            @RequestAttribute("userIdx") Long myIdx, // 내 ID (토큰)
            @PathVariable Long targetIdx             // 상대방 ID
    ) {
        String message = followService.toggleFollow(myIdx, targetIdx);
        return ResponseEntity.ok(message);
    }

    // 내가 구독한 사람 (팔로잉)
    @GetMapping("/following")
    public ResponseEntity<List<FollowDTO>> getFollowingList(@RequestAttribute("userIdx") Long myIdx) {
        return ResponseEntity.ok(followService.getFollowingList(myIdx));
    }

    // 나를 구독한 사람 (팔로워)
    @GetMapping("/follower")
    public ResponseEntity<List<FollowDTO>> getFollowerList(@RequestAttribute("userIdx") Long myIdx) {
        return ResponseEntity.ok(followService.getFollowerList(myIdx));
    }

    // 유저 검색 API
    @GetMapping("/search")
    public ResponseEntity<List<FollowDTO>> searchUser(
            @RequestAttribute("userIdx") Long myIdx,
            @RequestParam("keyword") String keyword
    ) {
        log.info("MHS");
        log.info("idx: {}", myIdx);
        log.info("keyword: {}", keyword);
        List<FollowDTO> lists = followService.searchUser(myIdx, keyword);
        log.info("size: {}", lists.size());
        return ResponseEntity.ok(lists);
    }
}