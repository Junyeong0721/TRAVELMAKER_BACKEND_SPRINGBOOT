package kr.soft.login.dto.Member;

import lombok.Data;

@Data
public class MemberLoginDTO {
    private Long idx;           // pk
    private String userId;      // id
    private String userPw;      // 비밀번호
    private String nickname;    // 닉네임
    private String mbti;        // mbti
    private String title;       // title
}
