package kr.soft.login.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanResponse {
    private Long planIdx;        // 계획 번호
    private String title;        // 계획 제목 ("AI 강릉 풀코스" 등)
    private LocalDateTime createAt; // 만든 날짜
    private List<DetailResponse> details; // 상세 일정 리스트

    @Data
    public static class DetailResponse {
        private int day;         // 1일차
        private String time;     // 12:00
        private String title;    // 장소명
        private String address;  // 주소
        private String category; // 카테고리
    }
}