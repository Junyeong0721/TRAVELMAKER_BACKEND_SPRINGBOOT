package kr.soft.login.dto.other;

import lombok.Data;

@Data
public class OtherProfileDTO {
    private Long userIdx;       // 유저 고유 번호
    private String nickname;    // 닉네임 (user-id)
    private String mbti;        // MBTI
    private String profileImg;  // 프로필 이미지 URL
    private int postCount;      // 게시물 수
    private int followerCount;  // 팔로워 수
    private int followingCount; // 팔로잉 수

    // 내가 이 사람을 팔로우했는지 여부 (true/false)
    private boolean isFollowed;
}