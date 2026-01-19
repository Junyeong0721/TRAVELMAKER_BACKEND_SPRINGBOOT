package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardWriteDTO {
    private Long Idx;
    private Long userIdx;
    private Long planIdx;
    private String title;
    private String content;
}
