package kr.soft.login.dto.Member;

import lombok.Data;

@Data
public class MemberLoginDTO {
    private Long userIdx;
    private String userId;
    private String userPw;

}
