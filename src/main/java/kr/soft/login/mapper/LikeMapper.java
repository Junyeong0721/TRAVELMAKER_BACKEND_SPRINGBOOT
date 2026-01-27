package kr.soft.login.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    int checkLike(Long postIdx, Long userIdx);

    void deleteLike(Long postIdx, Long userIdx);

    void insertLike(Long postIdx, Long userIdx);

}
