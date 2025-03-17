package com.example.datco.domain.service;

import com.example.datco.common.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtils jwtUtil;

    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않습니다.");
        }

        if (jwtUtil.isExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
        }

        if (!"refresh".equals(jwtUtil.getType(token))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("지원되지 않는 토큰입니다.");
        }

        Long id = Long.valueOf(jwtUtil.getId(token));
        String newAccessToken = jwtUtil.makeJwtToken(id);
        String newRefreshToken = jwtUtil.makeRefreshToken(id);

        response.addHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(makeCookie(newRefreshToken));

        return ResponseEntity.ok("토큰 재발급 성공");
    }

    private Cookie makeCookie(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}