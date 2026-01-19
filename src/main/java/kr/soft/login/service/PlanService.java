package kr.soft.login.service;

import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanMapper planMapper;

    @Transactional
    public void savePlan(PlanSaveRequest req) {
        // 1. plans 테이블에 저장 (저장 후 req.planIdx에 IDX값이 담김)
        planMapper.insertPlan(req);

        Long newPlanIdx = req.getPlanIdx(); // 생성된 PK 가져오기

        // 2. plan_detail 테이블에 리스트 반복 저장
        if (req.getDetails() != null) {
            for (PlanSaveRequest.DetailDto detail : req.getDetails()) {
                planMapper.insertDetail(newPlanIdx, detail);
            }
        }
    }
}