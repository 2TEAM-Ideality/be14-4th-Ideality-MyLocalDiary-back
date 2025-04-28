package com.leesang.mylocaldiary.security.kakao.controller;

import com.leesang.mylocaldiary.member.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.repository.MemberRepository;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import com.leesang.mylocaldiary.security.kakao.service.KakaoService;
import com.leesang.mylocaldiary.security.kakao.dto.KakaoUserInfoResponseDto;
import java.util.Date;
import java.util.Optional;
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
    public ResponseEntity<KakaoUserInfoResponseDto> callback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        String userEmail=userInfo.getKakaoAccount().getEmail();

        Optional<MemberEntity> optionalMember = memberRepository.findByEmail(userEmail);

        MemberEntity member;
        if (optionalMember.isEmpty()) {
            member = MemberEntity.builder()
                    .loginId(userEmail)
                    .nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                    .provider("kakao")  // 필요하면 추가
                    .providerId(userInfo.getId()+"")
                    .birth(new Date().toString())   // null 로 바꾸면 제거해야 하는 부분
                    .build();
            memberRepository.save(member);
        }else {
            member = optionalMember.get();
        }

        String jwtToken=jwtProvider.generateAccessToken(member.getId(),userEmail, "USER");
//        return ResponseEntity.ok(userInfo);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(userInfo);
    }
}