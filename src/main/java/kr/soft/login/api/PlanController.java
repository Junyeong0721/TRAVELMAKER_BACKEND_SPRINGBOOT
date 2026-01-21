package kr.soft.login.api;

import kr.soft.login.dto.PlanResponse;
import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // ★ 이거 남김
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // ★ 이것도 남김
@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    // ★ [보안 적용된 저장] workys 내용 유지
    @PostMapping("/save")
    public ResponseEntity<String> savePlan(
            @RequestAttribute("userIdx") Long userIdx, // 토큰에서 진짜 ID 받기
            @RequestBody PlanSaveRequest req
    ) {
        // 요청 데이터의 작성자를 진짜 로그인한 유저로 덮어씌우기
        req.setUserIdx(userIdx);

        planService.savePlan(req);
        log.info("저장된 데이터: {}", req.toString());
        return ResponseEntity.ok("저장 완료");
    }

    // ★ [보안 적용된 목록 조회] workys 내용 유지 (URL에 숫자 없음)
    @GetMapping("/list")
    public ResponseEntity<List<PlanResponse>> getMyPlans(
            @RequestAttribute("userIdx") Long userIdx
    ) {
        log.info("인터셉터가 넘겨준 userIdx: {}", userIdx);
        List<PlanResponse> list = planService.getMyPlans(userIdx);
        return ResponseEntity.ok(list);
    }

    // [추가] 삭제 API (여기는 충돌 없었음)
    @DeleteMapping("/{planIdx}")
    public ResponseEntity<String> deletePlan(@PathVariable Long planIdx) {
        planService.deletePlan(planIdx);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    @GetMapping("/{planIdx}")
    public ResponseEntity<PlanResponse> getPlan(@PathVariable Long planIdx) {
        return ResponseEntity.ok(planService.getPlan(planIdx));
    }

    // 수정 저장
    @PutMapping("/update")
    public ResponseEntity<String> updatePlan(@RequestBody PlanSaveRequest req) {
        planService.updatePlan(req);
        return ResponseEntity.ok("수정 완료");
    }
}