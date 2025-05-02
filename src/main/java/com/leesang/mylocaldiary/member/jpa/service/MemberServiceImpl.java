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
    public ResponseEntity<CommonResponseVO<?>> reissueAccessToken(String refreshToken) {
        log.info("Refresh token: " + refreshToken);

        // 1. Refresh 유효성 체크
        if (!jwtUtil.validateToken(refreshToken)) {
            log.info("Refresh token is invalid.");
            throw new GlobalException(ErrorCode.REFRESH_EXPIRED);
        }

        // 2. memberId 추출
        Integer memberId = jwtUtil.getUserIdFromToken(refreshToken).intValue();

        // 3. Redis에 저장된 Refresh Token과 비교
        String redisKey = "Refresh-Token:" + memberId;
        String savedRefreshToken = (String) redisTemplate.opsForValue().get(redisKey);

        if (savedRefreshToken == null || !refreshToken.equals(savedRefreshToken)) {
            throw new GlobalException(ErrorCode.TOKEN_NOT_EQUALS);
        }

        // 4. Access Token 재발급
        MemberEntity findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

        String newAccessToken = jwtProvider.generateAccessToken(
                memberId,
                findMember.getEmail(),
                String.valueOf(findMember.getMemberRole())
        );

        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("Access Token 재발급 완료")
                .data(Map.of("accessToken", newAccessToken))
                .build());
    }

    @Override
    @Transactional
    public void logout(String accessToken) {
        Long memberId = jwtUtil.getUserIdFromToken(accessToken);

        // 1. AccessToken 블랙리스트 처리
        long expiration = jwtUtil.getExpiration(accessToken);
        String blacklistKey = "Blacklist:" + accessToken;
        redisTemplate.opsForValue().set(blacklistKey, "logout", expiration, TimeUnit.MILLISECONDS);

        // 2. Redis에 저장된 RefreshToken 제거
        String refreshKey = "Refresh-Token:" + memberId;
        redisTemplate.delete(refreshKey);

    }
}
