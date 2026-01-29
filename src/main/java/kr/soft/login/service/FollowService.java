package kr.soft.login.service;

import kr.soft.login.dto.follow.FollowDTO;
import kr.soft.login.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowMapper followMapper;

    // 팔로우 / 언팔로우 토글 기능
    @Transactional
    public String toggleFollow(Long myIdx, Long targetIdx) {
        if (myIdx.equals(targetIdx)) return "본인은 팔로우할 수 없습니다.";

        // 1. 기록이 있는지 확인
        Map<String, Object> history = followMapper.findByFollowerAndFollowing(myIdx, targetIdx);

        if (history == null) {
            // 2-1. 기록 없음 -> 첫 팔로우 (INSERT)
            followMapper.insertFollow(myIdx, targetIdx);
            return "팔로우 성공";
        } else {
            // 2-2. 기록 있음 -> 상태만 변경 (UPDATE)
            String currentStatus = (String) history.get("DELETE_YN");

            if ("N".equals(currentStatus)) {
                // 이미 팔로우 중 -> 언팔로우 (Y로 변경)
                followMapper.updateFollowStatus(myIdx, targetIdx, "Y");
                return "언팔로우 성공";
            } else {
                // 언팔 상태 -> 다시 팔로우 (N으로 변경)
                followMapper.updateFollowStatus(myIdx, targetIdx, "N");
                return "다시 팔로우 성공";
            }
        }
    }

    public List<FollowDTO> getFollowingList(Long myIdx) {
        return followMapper.getFollowingList(myIdx);
    }

    public List<FollowDTO> getFollowerList(Long myIdx) {
        return followMapper.getFollowerList(myIdx);
    }
    public List<FollowDTO> searchUser(Long myIdx, String keyword) {
        return followMapper.searchUser(myIdx, keyword);
    }
}