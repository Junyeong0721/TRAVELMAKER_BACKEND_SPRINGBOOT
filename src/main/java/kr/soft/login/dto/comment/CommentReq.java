package kr.soft.login.dto.comment;

import lombok.Data;

@Data
public class CommentReq {
    private String content;
    private Long userIdx;
    private Long postIdx;
}
