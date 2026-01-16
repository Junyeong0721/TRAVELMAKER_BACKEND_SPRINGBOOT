package kr.soft.login.dto.Member;

import lombok.Data;

@Data
public class LoginRes {
    private String token;    // JWT 토큰
    private String nickname; // 화면 표시용
    private String mbti;
    private String userGrade;
}
