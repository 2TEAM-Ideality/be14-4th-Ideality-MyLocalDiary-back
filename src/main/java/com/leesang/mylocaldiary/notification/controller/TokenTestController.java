package com.leesang.mylocaldiary.notification.controller;

import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 어디 임시 테스트용 Controller 하나 만들어서
@RestController
public class TokenTestController {

    private final JwtProvider jwtProvider;

    public TokenTestController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/api/auth")
    public String createToken() {
        return jwtProvider.generateAccessToken(1, "test@email.com", "USER"); // userId = 1
    }
}
