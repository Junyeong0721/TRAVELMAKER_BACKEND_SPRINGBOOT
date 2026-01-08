package kr.soft.login.dto.Member;

import lombok.Data;

@Data
public class RegisterDTO {
    private String userId;
    private String userPw;
    private String userEmail;
    private String userNickname;

}
