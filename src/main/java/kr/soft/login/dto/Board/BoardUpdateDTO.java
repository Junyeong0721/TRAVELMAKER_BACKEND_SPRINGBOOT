package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardUpdateDTO {
    private Long idx;
    private String title;
    private String content;
    private String thumbnail;
    private Long planIdx;

}
