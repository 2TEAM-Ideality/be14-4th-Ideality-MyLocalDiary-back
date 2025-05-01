package com.leesang.mylocaldiary.security.jwt;

import com.leesang.mylocaldiary.common.exception.ErrorCode;
import com.leesang.mylocaldiary.common.exception.GlobalException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
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
            return e.getClaims();  // 만료되었지만 claims는 사용 가능
        } catch (JwtException | IllegalArgumentException e) {
            throw new GlobalException(ErrorCode.INVALID_TOKEN); // 🔥 다른 JWT 오류도 포괄적으로 처리
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
    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {  // ✅ 이름 일치
                    log.info(cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // MemberId(subject) 추출
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaimsAllowExpired(token).getSubject());
    }

    public long getExpiration(String accessToken) {
        return  getClaimsAllowExpired(accessToken).getExpiration().getTime();
    }
}
