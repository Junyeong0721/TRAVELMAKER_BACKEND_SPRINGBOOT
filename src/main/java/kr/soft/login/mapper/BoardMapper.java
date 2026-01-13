package kr.soft.login.mapper;

import kr.soft.login.dto.Board.BoardDetailDTO;
import kr.soft.login.dto.Board.BoardListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardListDTO> list();

    BoardDetailDTO detail(Long param);

}
