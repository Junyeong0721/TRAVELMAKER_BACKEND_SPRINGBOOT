package kr.soft.login.config.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 2. 외부에서 객체 생성을 못 하게 private 생성자 선언
    private PasswordUtil() {}

    /**
     * 비밀번호를 암호화합니다.
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 비밀번호가 일치하는지 확인합니다.
     * @param rawPassword 사용자 입력값
     * @param encodedPassword DB에 저장된 암호화된 값
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
