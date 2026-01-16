package kr.soft.login.api;


import kr.soft.login.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService followService;



}
