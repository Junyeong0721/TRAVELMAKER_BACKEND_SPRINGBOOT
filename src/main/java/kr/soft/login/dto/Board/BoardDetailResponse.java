package kr.soft.login.dto.Board;

import kr.soft.login.dto.comment.CommentDTO;
import kr.soft.login.dto.map.RoadmapDTO;
import lombok.Data;

import java.util.List;

@Data
public class BoardDetailResponse {
    private BoardDetailDTO post;          // 게시글 내용
    private List<RoadmapDTO> roadmap;     // 일정(로드맵) 리스트
    private List<CommentDTO> comments;
    private boolean mine;
    private boolean isCheckedLike;
}
