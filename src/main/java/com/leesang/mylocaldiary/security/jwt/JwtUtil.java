package com.leesang.mylocaldiary.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${token.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 비어있습니다.");
        }
        return false;
    }

    // Claims 추출
    public Claims getClaimsAllowExpired(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 🔥 만료되었어도 Claims 꺼내기
        }
    }

    // Access Token 추출
    public String extractAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // Refresh Token 추출
    public String extractRefreshToken(HttpServletRequest request) {
        return request.getHeader("Refresh-Token");
    }

    // MemberId(subject) 추출
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaimsAllowExpired(token).getSubject());
    }

    // Email Claim 추출
    public String getEmailFromToken(String token) {
        return getClaimsAllowExpired(token).get("email", String.class);
    }

    // Role Claim 추출
    public String getRoleFromToken(String token) {
        return getClaimsAllowExpired(token).get("role", String.class);
    }

    public long getExpiration(String accessToken) {
        return  getClaimsAllowExpired(accessToken).getExpiration().getTime();
    }
}
