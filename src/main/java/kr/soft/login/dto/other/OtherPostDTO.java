package kr.soft.login.dto.other;

import lombok.Data;

@Data
public class OtherPostDTO {
    private Long idx;           // 게시글 번호
    private String title;       // 제목
    private String thumbnail;   // 썸네일 이미지
    private String createAt;    // 작성일 (String으로 받음)
}