package kr.soft.login.mapper;

import kr.soft.login.dto.PlanSaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlanMapper {
    // plans 테이블 저장
    void insertPlan(PlanSaveRequest req);

    // plan_detail 테이블 저장
    void insertDetail(@Param("planIdx") Long planIdx, @Param("detail") PlanSaveRequest.DetailDto detail);
}