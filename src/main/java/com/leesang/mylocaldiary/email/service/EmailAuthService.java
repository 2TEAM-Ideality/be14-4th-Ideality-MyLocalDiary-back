package com.leesang.mylocaldiary.email.service;

import com.leesang.mylocaldiary.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* 설명. 단순 기능이라 Impl 분리 안했음 */
@Service
public class EmailAuthService {
    private final RedisUtil redisUtil;
    private static final String EMAIL_AUTH_PREFIX = "emailAuth:"; // Redis 키 앞에 붙일 접두사
    private static final long EMAIL_AUTH_TTL_MINUTES = 10; // 인증번호 유효시간 : 10분

    @Autowired
    public EmailAuthService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /* 설명. 이메일 인증 키 설정 */
    public void saveAuthCode(String email, String authCode) {
        String key = generateRedisKey(email);
        redisUtil.set(key, authCode, EMAIL_AUTH_TTL_MINUTES);
    }


    /* 설명. 이메일 인증번호 검증 */
    public boolean verifyAuthCode(String email, String authCode) {
        String key = generateRedisKey(email);
        String authCodeFromRedis = redisUtil.get(key);

        if (authCodeFromRedis == null) {
            return false;
        }
        return authCodeFromRedis.equals(authCode);
    }

    /* 설명. 이메일 인증번호 삭제 */
    public void deleteAuthCode(String email) {
        String key = generateRedisKey(email);
        redisUtil.delete(key);
    }

    /* 설명. 레디스에 저장할 키 이름 설정 */
    private String generateRedisKey(String email) {
        return EMAIL_AUTH_PREFIX + email;
    }
}
