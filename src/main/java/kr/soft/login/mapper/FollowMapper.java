package kr.soft.login.mapper;

import kr.soft.login.dto.follow.FollowDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FollowMapper {

    // 1. 팔로우 기록 조회 (Map으로 반환해서 DELETE_YN 상태 확인)
    // 파라미터가 2개 이상일 때는 @Param을 꼭 붙여야 XML에서 #{이름}으로 찾을 수 있습니다.
    Map<String, Object> findByFollowerAndFollowing(@Param("myIdx") Long myIdx, @Param("targetIdx") Long targetIdx);

    // 2. 첫 팔로우 (INSERT)
    void insertFollow(@Param("myIdx") Long myIdx, @Param("targetIdx") Long targetIdx);

    // 3. 상태 변경 (UPDATE)
    void updateFollowStatus(@Param("myIdx") Long myIdx, @Param("targetIdx") Long targetIdx, @Param("status") String status);

    // 4. 내가 팔로우한 목록
    List<FollowDTO> getFollowingList(Long myIdx);

    // 5. 나를 팔로우한 목록
    List<FollowDTO> getFollowerList(Long myIdx);

    // 검색 기능
    List<FollowDTO> searchUser(@Param("myIdx") Long myIdx, @Param("keyword") String keyword);
    int checkFollow(@Param("followerIdx") Long followerIdx, @Param("followingIdx") Long followingIdx);
}