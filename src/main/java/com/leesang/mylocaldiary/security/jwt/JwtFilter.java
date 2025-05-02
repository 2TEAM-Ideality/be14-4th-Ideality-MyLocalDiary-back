package com.leesang.mylocaldiary.security.jwt;

import com.leesang.mylocaldiary.redis.util.RedisUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;


    public JwtFilter(JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        // 1. 토큰 꺼내기
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7); // "Bearer " 제거

            // 2. 토큰 유효성 검증
            if (jwtUtil.validateToken(token)) {
                // 블랙리스트 검사
                String blacklistKey = "Blacklist:" + token;
                if (redisUtil.hasKey(blacklistKey)) {
                    log.warn("Access Token이 black list에 있음 - 인증 중단");
                    filterChain.doFilter(request, response);
                    return;
                }

                Claims claims = jwtUtil.getClaimsAllowExpired(token);
                String loginId = claims.get("email", String.class); // 🔥 우리가 넣은건 email (loginId)
                String role = claims.get("role", String.class);

                // 3. 인증 객체 만들어서 SecurityContext에 저장
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                loginId,
                                null,
                                Collections.singleton(() -> role) // 🔥 권한은 하나만 세팅
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("JWT 인증 성공 - loginId: {}", loginId);
                log.info("JWT 인증 성공 - role: {}", role);
            } else {
                log.warn("JWT 토큰이 유효하지 않음. 인증 처리 생략");
            }
        }

        // 4. 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}
