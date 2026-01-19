package kr.soft.login.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 🔥 이거 붙이면 모르는 필드 와도 에러 안 나고 무시함 (강추!)
public class AiPlanResponse {

    private List<AiPlanSet> sets;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AiPlanSet {
        private String id;
        private String day;
        private List<AiMemo> memos;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AiMemo {
        private String id;
        private String time;


        private String category; // RESTAURANT, CAFE, SIGHTSEEING
        private String address;  // 주소

        private String title;
        private String desc;
        private String reason;
        private List<String> tags;
    }
}