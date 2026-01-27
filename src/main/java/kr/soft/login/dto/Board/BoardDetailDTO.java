package kr.soft.login.dto.Board;

import lombok.Data;

@Data
public class BoardDetailDTO {
    private Long idx;            // p.IDX
    private String title;        // p.TITLE
    private String content;      // p.CONTENT
    private String nickname;     // m.NICKNAME
    private String mbti;         // m.MBTI
    private String userGrade;    // m.TITLE (유저 칭호)
    private String userComment;  // m.USER_COMMENT (작가 소개용)
    private int viewCount;       // p.VIEW_COUNT
    private String createAt;     // p.CREATE_AT
    private int likeCount;       // 좋아요 수 (별도 COUNT 쿼리 또는 JOIN)
    private Long userIdx;
    private boolean isFollowed;

}
