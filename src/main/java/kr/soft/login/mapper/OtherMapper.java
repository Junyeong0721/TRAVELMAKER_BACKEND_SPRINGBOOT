package kr.soft.login.mapper;

import kr.soft.login.dto.other.OtherPostDTO;
import kr.soft.login.dto.other.OtherProfileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OtherMapper {
    // 1. 타 유저 프로필 정보 (+ 내 팔로우 여부)
    OtherProfileDTO getProfile(@Param("targetUserIdx") Long targetUserIdx, @Param("myUserIdx") Long myUserIdx);

    // 2. 타 유저 게시글 리스트


    // limit, offset 추가
    List<OtherPostDTO> getPosts(@Param("targetUserIdx") Long targetUserIdx,
                                @Param("limit") int limit,
                                @Param("offset") int offset);
    // 개수 조회 추가
    int getPostCount(Long targetUserIdx);
}