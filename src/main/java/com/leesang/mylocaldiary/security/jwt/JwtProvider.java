package com.leesang.mylocaldiary.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final Key key;
    private final long expirationTimeInMillis;
    @Getter
    private final long refreshExpirationTimeInMillis;

    public JwtProvider(
            @Value("${token.secret}") String secretKey,
            @Value("${token.access_expiration_time}") long expirationTimeInMillis,
            @Value("${token.refresh_expiration_time}") long refreshExpirationTimeInMillis
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expirationTimeInMillis = expirationTimeInMillis;
        this.refreshExpirationTimeInMillis = refreshExpirationTimeInMillis;
    }

    public String generateAccessToken(Integer memberId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMillis);  // 12시간 설정

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(memberId.toString())
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 새로운 refresh token 생성
    public String generateRefreshToken(Integer memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpirationTimeInMillis);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(memberId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    // 🔥 토큰에서 Claims 꺼내기

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.getSubject()); // subject에 memberId가 들어있음
    }

}
