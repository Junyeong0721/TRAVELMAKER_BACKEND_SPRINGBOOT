package kr.soft.login.mapper;

import kr.soft.login.dto.MyInfoDTO.MyPageResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyInfoMapper {

    MyPageResponse getMyPageInfo(long userIdx);
}
