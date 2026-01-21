package kr.soft.login.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlanSaveRequest {
    private Long planIdx;        // DB의 IDX (Auto Increment된 값 받을 변수)
    private Long userIdx;        // USER_IDX
    private String title;        // TITLE
    private List<DetailDto> details;

    @Data
    public static class DetailDto {
        private int day;         // VISIT_ORDER (1, 2..)
        private String time;     // VISIT_TIME ("09:00")
        private String title;    // PLAN_TITLE ("우석정")
        private String address;  // ADDRESS
        private String category; // TYPES
    }
}