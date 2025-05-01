package com.leesang.mylocaldiary.member.jpa.service;

import com.leesang.mylocaldiary.common.exception.ErrorCode;
import com.leesang.mylocaldiary.common.exception.GlobalException;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private JwtUtil jwtUtil;
    private JwtProvider jwtProvider;
    private RedisTemplate redisTemplate;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository,
                             JwtUtil jwtUtil,
                             JwtProvider jwtProvider,
                             RedisTemplate redisTemplate) {
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.jwtProvider = jwtProvider;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponseVO<?>> reissueAccessToken(HttpServletRequest request) {
        // 1. 토큰 꺼내기 (헤더에서 추출)
        String refreshToken = jwtUtil.extractRefreshTokenFromCookie(request);
        log.info("Refresh token: " + refreshToken);

        // 2. Refresh 유효성 체크
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new GlobalException(ErrorCode.REFRESH_EXPIRED);
        }

        // 4. memberId 추출 (subject에 있음)
        Integer memberId = jwtUtil.getUserIdFromToken(refreshToken).intValue();

        // 5. Redis에 저장된 Refresh Token과 비교
        String redisKey = "Refresh-Token:" + memberId;
        String savedRefreshToken = (String) redisTemplate.opsForValue().get(redisKey);

        if (savedRefreshToken == null || !refreshToken.equals(savedRefreshToken)) {
            throw new GlobalException(ErrorCode.TOKEN_NOT_EQUALS);
        }

        // 6. Access 재발급 -> db에 해당하는 id 조회
        MemberEntity findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));


        String newAccessToken = jwtProvider.generateAccessToken(memberId
                , findMember.getEmail()
                , String.valueOf(findMember.getMemberRole()));

        // 7. 응답 반환
        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("Access Token 재발급 완료")
                .data(Map.of("accessToken", newAccessToken))
                .build());
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtUtil.extractAccessToken(request);
        Long memberId = jwtUtil.getUserIdFromToken(accessToken);

        // AccessToken 만료시간 계산
        long expiration = jwtUtil.getExpiration(accessToken);
        String blacklistKey = "Blacklist:" + accessToken;

        // 블랙리스트 추가
        redisTemplate.opsForValue().set(blacklistKey,
                "logout", expiration,
                TimeUnit.MILLISECONDS);

        // RefreshToken 제거
        String refreshKey = "Refresh-Token:" + memberId;
        redisTemplate.delete(refreshKey);

        // http-only 쿠키 제거
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
