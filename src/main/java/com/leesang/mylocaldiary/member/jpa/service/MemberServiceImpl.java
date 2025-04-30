package com.leesang.mylocaldiary.member.jpa.service;

import com.leesang.mylocaldiary.common.exception.ErrorCode;
import com.leesang.mylocaldiary.common.exception.GlobalException;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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
        String accessToken = jwtUtil.extractAccessToken(request);
        String refreshToken = jwtUtil.extractRefreshToken(request);

        // 2. Refresh 유효성 체크
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new GlobalException(ErrorCode.REFRESH_EXPIRED);
        }

        // 3. 블랙리스트 확인
        String blacklistKey = "Blacklist:" + accessToken;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(blacklistKey))) {
            throw new GlobalException(ErrorCode.TOKEN_BLACKLISTED);
        }
        // 4. memberId 추출 (subject에 있음)
        Long memberId = jwtUtil.getUserIdFromToken(refreshToken);

        // 5. Redis에 저장된 Refresh Token과 비교
        String redisKey = "Refresh-Token:" + memberId;
        String savedRefreshToken = (String) redisTemplate.opsForValue().get(redisKey);

        if (savedRefreshToken == null || !refreshToken.equals(savedRefreshToken)) {
            throw new GlobalException(ErrorCode.TOKEN_NOT_EQUALS);
        }

        // 6. Access 재발급 (만료된 access에서도 정보는 꺼낼 수 있음)
        String email = jwtUtil.getEmailFromToken(accessToken);
        String role = jwtUtil.getRoleFromToken(accessToken);

        String newAccessToken = jwtProvider.generateAccessToken(memberId.intValue(), email, role);

        // 7. 응답 반환
        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("Access Token 재발급 완료")
                .data(Map.of("accessToken", newAccessToken))
                .build());
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request) {
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

    }
}
