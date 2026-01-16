package kr.soft.login.api;

import kr.soft.login.common.ApiResponse;
import kr.soft.login.dto.Member.LoginReq;
import kr.soft.login.dto.Member.MemberLoginRes;
import kr.soft.login.dto.Member.RegisterDTO;
import kr.soft.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    private final RedisTemplate redisTemplate;
    // private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; //  *  시큐리티 = 암호화키 만들어주는 메서드를 가진 클래스


    // ✅ 로그인 API / URL 테스트 용
    @GetMapping("/test")
    public ResponseEntity<ApiResponse<MemberLoginRes>> login() {

        return ApiResponse.success(userService.login("super", "1234"));
    }
    

    // ✅ 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {


        return ApiResponse.success(userService.login(req.getUserId(), req.getUserPw()));
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO registerDTO) {
        userService.Register(registerDTO);

    }




}