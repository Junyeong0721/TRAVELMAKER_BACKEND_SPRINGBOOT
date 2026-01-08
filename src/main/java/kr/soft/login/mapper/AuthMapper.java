package kr.soft.login.mapper;

import kr.soft.login.dto.Member.MemberLoginDTO;
import kr.soft.login.dto.Member.RegisterDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    MemberLoginDTO login(String param);
    void register(RegisterDTO registerDTO);
}
