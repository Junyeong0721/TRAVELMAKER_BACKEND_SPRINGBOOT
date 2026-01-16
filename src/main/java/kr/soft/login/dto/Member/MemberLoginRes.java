package kr.soft.login.dto.Member;

import lombok.Builder;
import lombok.Data;

/**
 * Login 완료시, 보내주는 값
 */
@Data
@Builder
public class MemberLoginRes {

    private String accesstoken;     //사용자 토큰
    private String ninkname;        //사용자 닉네임
    private String mbti;            //사용자 MBTI
    private String title;           //사용자 칭호


    /**
     public MemberLoginRes() {}

    public MemberLoginRes(MemberLoginDTO member, String accesstoken) {
        this.accesstoken = accesstoken;
        this.mbti = member.getMbti();
        this.title = member.getTitle();
        this.ninkname = member.getNickname();
    }
     **/
}
