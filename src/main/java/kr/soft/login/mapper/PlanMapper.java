package kr.soft.login.mapper;

import kr.soft.login.dto.PlanDetailDto;
import kr.soft.login.dto.PlanResponse;
import kr.soft.login.dto.PlanSaveRequest;
import kr.soft.login.dto.plan.SelectPlanDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanMapper {
    // plans 테이블 저장
    void insertPlan(PlanSaveRequest req);

    // plan_detail 테이블 저장
    void insertDetail(@Param("planIdx") Long planIdx, @Param("detail") PlanSaveRequest.DetailDto detail);

    List<PlanResponse> selectPlansByUser(Long userIdx);

    // [추가] 상세 일정 조회
    List<PlanDetailDto> selectPlanDetails(Long planIdx);

    // [추가] 계획 삭제
    void deletePlan(Long planIdx);

    PlanResponse selectPlanById(Long planIdx);

    void updatePlanTitle(Long planIdx, String title);

    void deleteDetailsByPlanIdx(Long planIdx);

    List<SelectPlanDTO> selectplan(Long userIdx);

}