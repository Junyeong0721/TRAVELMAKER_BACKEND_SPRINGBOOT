package kr.soft.login.dto.map;

import lombok.Data;

@Data
public class RoadmapDTO {
    private String planTitle;    // pd.PLAN_TITLE (일정명)
    private int visitOrder;      // pd.VISIT_ORDER (순서)
    private String visitTime;    // pd.VISIT_TIME (시간: 14:00:00)
    private String memo;         // pd.MEMO (상세 설명)
    private String types;        // pd.TYPES (교통, 관광 등 - 태그로 활용)
    private String address;      // pd.ADDRESS (주소)
}
