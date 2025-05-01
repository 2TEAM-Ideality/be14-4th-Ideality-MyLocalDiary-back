package com.leesang.mylocaldiary.member.jpa.controller;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.jpa.service.MemberService;
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

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/testJwtFilter")
    public String testJwtFilter() {
        return "testJwtFilter";
    }

    /* 설명. 토큰 재발급 */
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponseVO<?>> reissueToken(HttpServletRequest request) {
        return memberService.reissueAccessToken(request);
    }

    /* 설명. 로그아웃(토큰 무효화) */
    @PostMapping("/logout")
    public ResponseEntity<CommonResponseVO<?>> logout(HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(request, response);
        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("로그아웃이 완료되었습니다.")
                .data(null)
                .build());
    }
}
