package com.leesang.mylocaldiary.security.filter;

import com.leesang.mylocaldiary.security.jwt.JwtUtil;
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

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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
                Claims claims = jwtUtil.getClaims(token);

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
            }
        }

        // 4. 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}
