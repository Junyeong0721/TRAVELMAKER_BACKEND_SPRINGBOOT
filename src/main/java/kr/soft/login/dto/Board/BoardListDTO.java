package kr.soft.login.dto.Board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDTO {
    private Long idx;
    private String title;
    private String nickname;
    private String userGrade;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String thumbnail;

}
