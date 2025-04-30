package com.leesang.mylocaldiary.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환용
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401로 세팅
        response.setContentType("application/json;charset=UTF-8");

        CommonResponseVO<?> res = CommonResponseVO.builder().
                status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("잘못된 인증 정보입니다.")
                .data(null).
                build();

        response.getWriter().write(objectMapper.writeValueAsString(res));
    }
}
