package com.leesang.mylocaldiary.member.jpa.controller;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.jpa.service.MemberService;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/testJwtFilter")
    public String testJwtFilter() {
        return "testJwtFilter";
    }

    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponseVO<?>> reissueToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refresh-token");
        return memberService.reissueAccessToken(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponseVO<?>> logout(HttpServletRequest request) {
        String accessToken = jwtUtil.extractAccessToken(request); // Authorization 헤더에서 추출
        memberService.logout(accessToken);

        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("로그아웃이 완료되었습니다.")
                .data(null)
                .build());
    }
}
