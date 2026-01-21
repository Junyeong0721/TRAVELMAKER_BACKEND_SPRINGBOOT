package kr.soft.login.api;

import kr.soft.login.dto.PlanResponse;
import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping("/save")
    public ResponseEntity<String> savePlan(@RequestBody PlanSaveRequest req) {
        planService.savePlan(req);
        return ResponseEntity.ok("저장 완료");
    }
    // [추가] 내 계획 불러오기 API

    // GET /plans/list/1 (뒤에 유저 번호)
    @GetMapping("/list/{userIdx}")
    public ResponseEntity<List<PlanResponse>> getMyPlans(@PathVariable Long userIdx) {
        List<PlanResponse> list = planService.getMyPlans(userIdx);
        return ResponseEntity.ok(list);
    }

    // [추가] 삭제 API
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