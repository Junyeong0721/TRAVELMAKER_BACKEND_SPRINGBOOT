package kr.soft.login.dto.follow;

import lombok.Data;

@Data
public class FollowDTO {
    private Long idx;           // follow 테이블의 고유 번호 (PK)
    private Long userIdx;       // 유저 번호 (member.idx)
    private String nickname;    // 닉네임
    private String mbti;        // MBTI
    private String profileImage;// 프로필 사진
    private String statusMessage; // 상태 메시지

    // 이 필드는 XML 쿼리에서 'isFollowBack'으로 값을 넣어줘야 합니다.
    private boolean isFollowBack; // 맞팔 여부 (true/false)

    // 즐겨찾기 여부 (필요 시 사용)
    private String favoriteYn;
}