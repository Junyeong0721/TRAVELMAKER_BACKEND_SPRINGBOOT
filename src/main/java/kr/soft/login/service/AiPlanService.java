package kr.soft.login.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.soft.login.dto.AiPlanRequest;
import kr.soft.login.dto.AiPlanResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.InputStream;
import java.util.Comparator; // [추가] 정렬을 위한 import
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiPlanService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 1️⃣ 엑셀 파일 읽기 (음식 유형 포함)
    private String loadExcelData() {
        StringBuilder sb = new StringBuilder();
        try {
            ClassPathResource resource = new ClassPathResource("shops.xlsx");
            if (!resource.exists()) return "";

            InputStream inputStream = resource.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            sb.append("\n[🔥 우선 추천 대상 (엑셀 데이터) 🔥]\n");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 헤더 제외
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // B열: 이름, E열: 주소, F열: 음식유형(한식, 카페 등)
                String name = getCellValue(row.getCell(1));
                String addr = getCellValue(row.getCell(4));
                String type = getCellValue(row.getCell(5));

                if (!name.isEmpty()) {
                    sb.append(String.format("- %s (위치: %s, 분류: %s)\n", name, addr, type));
                }
            }
            workbook.close();
        } catch (Exception e) {
            log.error("엑셀 로드 실패", e);
            return "";
        }
        return sb.toString();
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    // [수정] 프롬프트에 시간 포맷 지침 추가 (4번 항목)
    private static final String SYSTEM_PROMPT = """
            너는 "강릉 MBTI 여행 플래너"다.
            사용자 성향과 엑셀 데이터를 바탕으로 여행 계획을 짜고, 각 장소를 3가지 카테고리로 명확히 분류해야 한다.
            
            [카테고리 분류 기준]
            - RESTAURANT: 식사하는 곳 (한식, 양식, 횟집 등)
            - CAFE: 커피, 디저트, 베이커리
            - SIGHTSEEING: 관광지, 해변, 체험 활동, 공원
            
            [🔴 중요: 시간표 고정 규칙]
            1. 09:00 (아침) -> 무조건 'RESTAURANT' 분류만 배치해라.
            2. 12:00 (점심) -> 무조건 'RESTAURANT' 분류만 배치해라. (카페 금지)
            3. 18:00 (저녁) -> 무조건 'RESTAURANT' 분류만 배치해라. (카페 금지)            4. 관광/카페: 위 식사 시간 사이(예: 10:30, 14:00, 16:00, 20:00)에 배치해라.
            
            [지침]
            1. 엑셀 리스트에 있는 가게를 추천할 경우, 엑셀의 '분류'를 참고해서 카테고리를 정해라.
            2. 엑셀에 없는 관광지는 네 지식으로 추천하고 'SIGHTSEEING'으로 분류해라.
            3. 'address' 필드는 필수다.
            4. 'time' 필드는 반드시 'HH:mm' 포맷(24시간제)을 사용해라. (예: 14:00, 09:30)
            5. [중요] 전체 여행 일정 내에서 '가게명(title)'은 절대 중복되면 안 된다. 같은 장소를 두 번 추천하지 마라.
            """;
            ;



    // 2️⃣ 스키마에 'category' 필드 추가
    private Map<String, Object> responseFormatJsonSchema(int days) {
        Map<String, Object> memoSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "id", Map.of("type", "string"),
                        "time", Map.of("type", "string"),
                        "category", Map.of(
                                "type", "string",
                                "enum", List.of("RESTAURANT", "CAFE", "SIGHTSEEING"),
                                "description", "장소 유형 (식당, 카페, 관광지)"
                        ),
                        "title", Map.of("type", "string", "description", "가게명"),
                        "desc", Map.of("type", "string"),
                        "address", Map.of("type", "string"),
                        "reason", Map.of("type", "string"),
                        "tags", Map.of("type", "array", "items", Map.of("type", "string"))
                ),
                "required", List.of("id", "time", "category", "title", "desc", "address", "reason", "tags"),
                "additionalProperties", false
        );

        Map<String, Object> setSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "id", Map.of("type", "string"),
                        "day", Map.of("type", "string"),
                        "memos", Map.of("type", "array", "minItems", 5, "items", memoSchema)
                ),
                "required", List.of("id", "day", "memos"),
                "additionalProperties", false
        );

        Map<String, Object> rootSchema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "sets", Map.of("type", "array", "minItems", days, "maxItems", days, "items", setSchema)
                ),
                "required", List.of("sets"),
                "additionalProperties", false
        );

        return Map.of("type", "json_schema", "json_schema", Map.of("name", "gangneung_plan", "strict", true, "schema", rootSchema));
    }

    public AiPlanResponse generatePlan(AiPlanRequest req) {
        String partner = (req.getPartnerMbti() == null || req.getPartnerMbti().isBlank()) ? "없음" : req.getPartnerMbti();
        String msg = (req.getMessage() == null || req.getMessage().isBlank()) ? "없음" : req.getMessage();
        String budget = (req.getDailyBudget() == null) ? "미정" : String.valueOf(req.getDailyBudget());

        String d = req.getDuration();
        int days = 1;
        if (d != null) {
            if (d.contains("1박") || d.contains("ONE_NIGHT")) days = 2;
            else if (d.contains("2박") || d.contains("TWO_NIGHTS")) days = 3;
            else if (d.contains("3박") || d.contains("THREE_NIGHTS")) days = 4;
        }

        String excelData = loadExcelData();

        String userPrompt = """
                [여행 조건]
                - 기간: %s (총 %d일)
                - 인원: %d명
                - MBTI: 나(%s), 동행(%s)
                - 1인 예산: %s만원
                - 추가 요청: %s
                
                %s
                
                [미션]
                1. 엑셀 리스트를 참고하여 %d일치 일정을 짜줘.
                2. 각 장소를 [RESTAURANT, CAFE, SIGHTSEEING] 중 하나로 반드시 분류해줘.
                3. MBTI(%s) 맞춤 추천 사유를 적어줘.
                """.formatted(
                d, days,
                req.getPeople(), req.getMyMbti(), partner, budget, msg,
                excelData,
                days, req.getMyMbti()
        );

        Map<String, Object> payload = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "response_format", responseFormatJsonSchema(days)
        );

        String raw = openAiWebClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            JsonNode root = objectMapper.readTree(raw);
            String content = root.at("/choices/0/message/content").asText();

            // 1. JSON을 객체로 변환
            AiPlanResponse response = objectMapper.readValue(content, AiPlanResponse.class);

            // [추가됨] 2. 응답받은 데이터를 시간(time) 순서대로 정렬 (오름차순)
            if (response.getSets() != null) {
                for (var set : response.getSets()) {
                    if (set.getMemos() != null) {
                        set.getMemos().sort(Comparator.comparing(memo -> {
                            String t = memo.getTime();
                            // null 방지 및 문자열 비교 ("09:00" < "13:00")
                            return t == null ? "" : t;
                        }));
                    }
                }
            }

            return response;

        } catch (Exception e) {
            log.error("AI 파싱 에러", e);
            throw new RuntimeException("AI 응답 파싱 실패", e);
        }
    }
}