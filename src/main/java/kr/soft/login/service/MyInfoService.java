package kr.soft.login.service;

import kr.soft.login.dto.MyInfoDTO.MyPageResponse;
import kr.soft.login.mapper.MyInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

@Slf4j
@Service
public class MyInfoService {

    @Autowired
    private MyInfoMapper myInfoMapper;

    public MyPageResponse getMyPageInfo(long idx) {
        return myInfoMapper.getMyPageInfo(idx);
    }

}
