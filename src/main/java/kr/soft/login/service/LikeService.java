package kr.soft.login.service;

import kr.soft.login.mapper.LikeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeService {

    @Autowired
    LikeMapper likeMapper;

    public boolean checkLike(Long postIdx, Long userIdx) {
        return likeMapper.checkLike(postIdx, userIdx) > 0;
    }


    public void addLike(Long postIdx, Long userIdx) {
        // 중복 방지를 위해 확인 후 인서트 (DB 제약조건이 있지만 서비스에서도 체크)
            likeMapper.insertLike(postIdx, userIdx);
    }


    public void removeLike(Long postIdx, Long userIdx) {
        likeMapper.deleteLike(postIdx, userIdx);
    }




}
