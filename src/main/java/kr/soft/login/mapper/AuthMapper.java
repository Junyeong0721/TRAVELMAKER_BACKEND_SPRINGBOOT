package kr.soft.login.mapper;

import kr.soft.login.dto.Member.MemberLoginDTO;
import kr.soft.login.dto.Member.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    /**
     * 로그인 프로세스
     * @param param
     * @return
     */
    MemberLoginDTO login(String param);

    /**
     * 회원가입 프로세스
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);
}
