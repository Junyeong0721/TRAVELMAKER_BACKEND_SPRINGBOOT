package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardEditDTO {
    private Long postIdx;
    private String title;
    private String content;
    private Long planIdx;
    private String thumbnail;
}
