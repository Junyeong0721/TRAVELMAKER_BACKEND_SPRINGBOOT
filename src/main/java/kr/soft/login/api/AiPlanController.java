package kr.soft.login.api;

import kr.soft.login.dto.AiPlanRequest;
import kr.soft.login.dto.AiPlanResponse;
import kr.soft.login.service.AiPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiPlanController {

    private final AiPlanService aiPlanService;

    @PostMapping("/plan")
    public AiPlanResponse plan(@RequestBody AiPlanRequest req) {
        return aiPlanService.generatePlan(req);
    }
}
