package com.leesang.mylocaldiary.security.kakao.controller;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberStatus;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberProvider;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberRole;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import com.leesang.mylocaldiary.security.kakao.dto.LoginResponseDto;
import com.leesang.mylocaldiary.security.kakao.service.KakaoService;
import com.leesang.mylocaldiary.security.kakao.dto.KakaoUserInfoResponseDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @GetMapping("/callback")
    public ResponseEntity<CommonResponseVO<Object>> callback(@RequestParam("code") String code
    , HttpServletResponse response) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        String userEmail=userInfo.getKakaoAccount().getEmail();
        String providerId=userInfo.getId()+"";

        Optional<MemberEntity> optionalMember = memberRepository.findByProviderAndProviderId(MemberProvider.KAKAO, providerId);

        MemberEntity member;
        String responseMessage="";

        if (optionalMember.isEmpty()) {
            member = MemberEntity.builder()
                    .email(userEmail)
                    .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                    .createdAt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .provider(MemberProvider.KAKAO)
                    .providerId(providerId)
                    .status(MemberStatus.ACTIVE)
                    .memberRole(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
            log.info("member registered");
            responseMessage="카카오 계정으로 회원가입 성공";
        }else {
            member = optionalMember.get();
            log.info("member login");
        }

        String jwtToken=jwtProvider.generateAccessToken(member.getId(),userEmail, "ROLE_MEMBER");
        log.info("jwtToken={}", jwtToken);
        String refreshToken = jwtProvider.generateRefreshToken(member.getId());
        log.info("refreshToken={}", refreshToken);

        Cookie refreshTokenCookie=new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge((int) (jwtProvider.getRefreshExpirationTimeInMillis() / 1000)); // 밀리초 → 초

        response.addCookie(refreshTokenCookie);

        if(responseMessage.length()==0){
            responseMessage="카카오 계정으로 로그인 성공";
        }else{
            responseMessage+=", 카카오 계정으로 로그인 성공";
        }

        // 🔥 CommonResponseVO 형태로 만들어서 JSON 변환
        CommonResponseVO<Object> commonResponse = CommonResponseVO.builder()
                .status(200)
                .message("로그인 성공")
                .data(Map.of("accessToken", jwtToken))
                .build();

        log.debug("callback end");
        return ResponseEntity.ok(commonResponse);
    }
}