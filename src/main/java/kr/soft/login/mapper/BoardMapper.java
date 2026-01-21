package kr.soft.login.mapper;

import kr.soft.login.dto.Board.BoardDetailDTO;
import kr.soft.login.dto.Board.BoardListDTO;
import kr.soft.login.dto.Board.BoardWriteDTO;
import kr.soft.login.dto.comment.CommentDTO;
import kr.soft.login.dto.comment.CommentReq;
import kr.soft.login.dto.map.RoadmapDTO;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.stream.events.Comment;
import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardListDTO> list(int offset);

    BoardDetailDTO detail(Long param);

    List<RoadmapDTO> roadmap(Long param);

    List<CommentDTO> comment(Long param);

    void write(BoardWriteDTO boardWriteDTO);

    void plusViewCount(Long param);

    int count();

    void insertcomment(CommentReq req);
}
