package kr.soft.login.dto.comment;

import lombok.Data;

@Data
public class commentDTO {
    private Long idx;            // c.IDX
    private String nickname;     // m.NICKNAME
    private String mbti;         // m.MBTI
    private String content;      // c.CONTENT
    private String createAt;
}
