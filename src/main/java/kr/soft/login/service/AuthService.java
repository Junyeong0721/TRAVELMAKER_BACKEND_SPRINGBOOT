package kr.soft.login.service;

import kr.soft.login.config.jwt.JwtTokenProvider;
import kr.soft.login.config.util.PasswordUtil;
import kr.soft.login.dto.Member.MemberLoginDTO;
import kr.soft.login.dto.Member.MemberLoginRes;
import kr.soft.login.dto.Member.RegisterDTO;
import kr.soft.login.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisTokenService redisTokenService;

    @Autowired
    private AuthMapper authMapper;



    // ✅ 로그인 처리

    /**
     * @param ( 사용자 ID, PW 필요 ) - 로그인 구분 용도
     * @return Token 정보
                */
    public String login(String id, String pw) {

        MemberLoginDTO  resultDTO = authMapper.login(id);

        log.info("login resultDTO:{}", resultDTO);
            // ex) memberMapper.login()
        if(resultDTO == null) {
            return null;
        }
        log.info("login pw:{}", resultDTO.getUserPw());

        if(!PasswordUtil.matches(pw, resultDTO.getUserPw())) {
            log.info("비밀번호 불일치");
            return null;
        }
            /**************
            * ✅ JWT 생성
            ***************/


        //2. JWT 토큰 만들기
        String accessToken = jwtTokenProvider.createAccessToken(resultDTO.getUserIdx(), resultDTO.getUserId());

        
        
        /**************
         * ✅ REDIS 저장하기
         ***************/

        // ✅ UNIQUE이면 무엇이가든 가능
        //3. Redis 등록 (access:userId 형태)
        redisTokenService.saveAccessToken(resultDTO.getUserId(), accessToken);

        MemberLoginRes res = MemberLoginRes
                .builder()
                .title(resultDTO.getTitle())
                .ninkname(resultDTO.getNickname())
                .mbti(resultDTO.getMbti())
                .accesstoken(accessToken)
                .build();

//        MemberLoginRes res = new MemberLoginRes(resultDTO, accessToken);


        return accessToken;
    }
    public void Register(RegisterDTO registerDTO) {

        String encodedPw = PasswordUtil.encode(registerDTO.getUserPw());

        registerDTO.setUserPw(encodedPw);

        log.info("encoding pw :{}", registerDTO.getUserPw());

        authMapper.register(registerDTO);
    }



}