package kr.soft.login.service;

import kr.soft.login.dto.PlanDetailDto;
import kr.soft.login.dto.PlanResponse;
import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.mapper.PlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    // [추가] 사용자별 계획 전체 조회
    public List<PlanResponse> getMyPlans(Long userIdx) {
        // 1. 계획 껍데기(부모) 리스트 가져오기
        List<PlanResponse> plans = planMapper.selectPlansByUser(userIdx);

        // 2. 각 계획마다 상세 일정(자식) 채워 넣기
        for (PlanResponse p : plans) {
            List<PlanDetailDto> details = planMapper.selectPlanDetails(p.getPlanIdx());
            p.setDetails(details);
        }

        return plans;
    }
    // [추가] 계획 삭제 로직
    @Transactional
    public void deletePlan(Long planIdx) {
        planMapper.deletePlan(planIdx);
        // 1. 딸린 세부 일정들(details)을 먼저 다 삭제
        planMapper.deletePlanDetails(planIdx);

        // 2. 그 다음 껍데기(plan) 삭제
        planMapper.deletePlan(planIdx);
    }


    // 1. 단건 조회
    public PlanResponse getPlan(Long planIdx) {
        PlanResponse plan = planMapper.selectPlanById(planIdx);
        if(plan != null) {
            plan.setDetails(planMapper.selectPlanDetails(planIdx));
        }
        return plan;
    }

    // 2. 수정 (Update)
    @Transactional
    public void updatePlan(PlanSaveRequest req) {
        // 1. 부모(Title) 수정
        planMapper.updatePlanTitle(req.getPlanIdx(), req.getTitle());

        // 2. 자식(Details) 싹 지우고 다시 저장 (가장 확실한 방법)
        planMapper.deleteDetailsByPlanIdx(req.getPlanIdx());

        if (req.getDetails() != null) {
            for (PlanSaveRequest.DetailDto detail : req.getDetails()) {
                planMapper.insertDetail(req.getPlanIdx(), detail);
            }
        }
    }
}