package kr.soft.login.mapper;

import kr.soft.login.dto.Member.MemberLoginDTO;
import kr.soft.login.dto.Member.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    /**
     * 로그인 프로세스
     * 작성일: 26-01-06
     * 작업자: 명희승
     * @param param
     * @return MemberLoginDTO
     *     Long idx;           // pk
     *     String userId;      // id
     *     String userPw;      // 비밀번호
     *     String nickname;    // 닉네임
     *     String mbti;        // mbti
     *     String title;       // title
     */
    MemberLoginDTO login(String param);

    /**
     * 회원가입 프로세스
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);
}
