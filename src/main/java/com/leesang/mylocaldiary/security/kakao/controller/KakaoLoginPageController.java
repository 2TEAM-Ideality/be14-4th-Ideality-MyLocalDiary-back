package com.leesang.mylocaldiary.security.kakao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class KakaoLoginPageController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/kakao")
    public String kakaoLoginRedirect() {
        log.info("kakaoLoginRedirect Start");
        System.out.println("@@@");
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + client_id
                + "&redirect_uri=" + redirect_uri;
        log.info("kakaoLoginRedirect End");
        return "redirect:" + kakaoAuthUrl;
    }
}
