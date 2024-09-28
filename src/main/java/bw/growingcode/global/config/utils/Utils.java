package bw.growingcode.global.config.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utils {

    // private 생성자: 인스턴스 생성 불가
    private Utils() {
        // Private constructor to prevent instantiation
    }

    // decodeString 메서드를 static으로 선언
    public static String decodeString(String encodedString) {
        // 디코딩 로직 구현
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedString);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
