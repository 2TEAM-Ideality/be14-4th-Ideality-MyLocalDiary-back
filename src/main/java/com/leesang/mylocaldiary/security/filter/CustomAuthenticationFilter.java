package com.leesang.mylocaldiary.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesang.mylocaldiary.auth.dto.RequestLoginDTO;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.leesang.mylocaldiary.security.details.CustomUserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            log.info("애가 가져감");
            // 1. request의 JSON Body를 파싱해서 RequestLoginVO에 매핑
            ObjectMapper objectMapper = new ObjectMapper();
            RequestLoginDTO creds = objectMapper.readValue(request.getInputStream(), RequestLoginDTO.class);

            log.info("로그인 시도 - loginId: {}", creds.getLoginId());

            // 2. UsernamePasswordAuthenticationToken 생성해서 AuthenticationManager에 전달
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLoginId(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        log.info("로그인 성공: {}", authResult);

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        Integer memberId = userDetails.getMemberId();           // 🔥 DB 기본키
        String email = userDetails.getEmail();            // 🔥 로그인 ID = 이메일
        String role = userDetails.getAuthorities().iterator().next().getAuthority(); // 첫 번째 권한

        String accessToken = jwtProvider.generateAccessToken(memberId, email, role);

        // 🔥 Content-Type을 JSON으로 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 🔥 CommonResponseVO 형태로 만들어서 JSON 변환
        CommonResponseVO<Object> commonResponse = CommonResponseVO.builder()
                .status(200)
                .message("로그인 성공")
                .data(Map.of("accessToken", accessToken))
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(commonResponse);

        response.getWriter().write(jsonResponse);

        log.info("AccessToken 발급 완료, JSON Body로 반환 (CommonResponseVO 형태)");
        log.info("AccessToken 발급 완료: {}", accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        log.info("로그인 실패: {}", failed.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

        CommonResponseVO<Object> errorResponse = CommonResponseVO.builder()
                .status(401)
                .message("로그인 실패: " + failed.getMessage())
                .data(null)
                .build();

        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }
}

