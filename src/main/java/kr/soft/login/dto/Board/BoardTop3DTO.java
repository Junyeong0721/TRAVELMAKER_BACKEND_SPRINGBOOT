package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardTop3DTO {
    private Long idx;
    private String title;
    private String content;
    private String thumbnail;
    private int viewCount;
    private int likeCount;
    private int commentCount;
}
