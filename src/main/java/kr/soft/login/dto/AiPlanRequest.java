package kr.soft.login.dto;

import lombok.Data;

@Data
public class AiPlanRequest {
    private String city;        // "강원도"
    private String district;    // "강릉시"
    private String myMbti;      // ENFP ...
    private String partnerMbti; // none / SAME / ENFP ...
    private Integer people;     // 1
    private String duration;    // "당일치기" ...
    private String dailyBudget; // "10만원" ...
    private String message;     // 채팅 추가요청(옵션)
}
