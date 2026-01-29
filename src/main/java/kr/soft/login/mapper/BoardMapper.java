package kr.soft.login.mapper;

import kr.soft.login.dto.Board.*;
import kr.soft.login.dto.comment.CommentDTO;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.map.RoadmapDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.stream.events.Comment;
import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardListDTO> list(int offset);

    List<BoardListDTO> bestlist(int offset);

    List<BoardListDTO> mylist(BoardMyListParam boardMyListParam);

    BoardDetailDTO detail(@Param("idx") Long idx, @Param("userIdx") Long userIdx);
    List<RoadmapDTO> roadmap(Long param);

    List<CommentDTO> comment(Long param);

    void write(BoardWriteDTO boardWriteDTO);

    void plusViewCount(Long param);

    long mine(Long param);

    BoardEditDTO edit(Long param);

    int count();

    void insertcomment(CommentReq req);

    void updateBoard(BoardUpdateDTO boardUpdateDTO);

    void deletePost(Long idx);

    List<BoardTop3DTO> top3();
}
