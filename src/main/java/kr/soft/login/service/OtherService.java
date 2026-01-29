package kr.soft.login.service;

import kr.soft.login.dto.other.OtherPostDTO;
import kr.soft.login.dto.other.OtherProfileDTO;
import kr.soft.login.mapper.OtherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherService {

    @Autowired
    private OtherMapper otherMapper;

    public Map<String, Object> getOtherPageData(Long targetUserIdx, Long myUserIdx,int page) {
        Map<String, Object> result = new HashMap<>();

        // 1. 프로필 정보 가져오기
        OtherProfileDTO profile = otherMapper.getProfile(targetUserIdx, myUserIdx);

        int limit = 6;
        int offset = (page - 1) * limit;

        // 2. 게시글 리스트 가져오기
        List<OtherPostDTO> posts = otherMapper.getPosts(targetUserIdx, limit, offset);
        int totalCount = otherMapper.getPostCount(targetUserIdx);
        int totalPages = (int) Math.ceil((double) totalCount / limit);

        result.put("profile", profile);
        result.put("posts", posts);

        return result;
    }
}