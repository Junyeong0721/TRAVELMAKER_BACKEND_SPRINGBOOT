package kr.soft.login.api;

import kr.soft.login.dto.MyInfoDTO.MyPageResponse;
import kr.soft.login.mapper.MyInfoMapper;
import kr.soft.login.service.MyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/myinfo")
public class MyInfoController {

    @Autowired
    MyInfoService myInfoService;

    @GetMapping("/mypageinfo")
    public MyPageResponse getMyPageInfo(@RequestAttribute("userIdx") long idx) {
        MyPageResponse myPageResponse = myInfoService.getMyPageInfo(idx);
        log.info("myPageResponse={}", myPageResponse.toString());
        return myPageResponse;
    }

}
