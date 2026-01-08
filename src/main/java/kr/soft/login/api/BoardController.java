package kr.soft.login.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//수정

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardController {


    @GetMapping("/list")
    public void list() {
        log.info("board list success");

    }
}
