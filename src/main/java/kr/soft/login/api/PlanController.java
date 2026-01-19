package kr.soft.login.api;

import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}