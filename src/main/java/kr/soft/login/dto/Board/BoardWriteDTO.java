package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardWriteDTO {
    private Long Idx;
    private Long userIdx;
    private Long planIdx;
    private String title;
    private String content;
    private String thumbnail; // ✅ 추가: DB에 저장될 파일명(String)을 담는 칸
}