package kr.soft.login.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.soft.login.config.jwt.JwtTokenProvider;
import kr.soft.login.service.RedisTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenService redisTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/ai/") || uri.startsWith("/api/auth/")) {
            return true; // ✅ AI, auth는 토큰 검사 없이 통과
        }

        String method = request.getMethod();

        log.info("URI: {}", uri);
        log.info("METHOD: {}", method);

        // ✅ 1) OPTIONS 요청은 인증 대상이 아님 (무조건 통과)
        if ("OPTIONS".equalsIgnoreCase(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // ✅ 2) AI/Auth는 인증 스킵 (개발/테스트용)
        //    - 로그인/회원가입
        //    - AI 추천 API
        if (uri.startsWith("/api/auth/") || uri.startsWith("/api/ai/")) {
            return true;
        }

        // ✅ 3) Authorization 헤더 검사
        String authorization = request.getHeader("Authorization");
        log.info("Authorization: {}", authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authorization.substring(7);

        // ✅ 4) JWT 자체 유효성 검증
        if (!jwtTokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("❌ 유효하지 않은 JWT 토큰입니다.");
            return false;
        }

        String userId = jwtTokenProvider.getUserId(token);

        // ✅ 5) Redis에 등록된 토큰인지 확인
        if (!redisTokenService.existsAccessToken(userId, token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("⚠️ Redis에 등록되지 않은 토큰입니다.");
            return false;
        }

        // ✅ 6) TTL 갱신 (30분)
        redisTokenService.refreshAccessTokenTTL(userId);

        return true;
    }
}
