package com.leesang.mylocaldiary.email.service;

import com.leesang.mylocaldiary.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* 설명. 단순 기능이라 Impl 분리 안했음 */
@Service
public class EmailAuthService {
    private final RedisUtil redisUtil;
    private static final String EMAIL_AUTH_PREFIX = "emailAuth:"; // 이메일 인증에 대한 접두사
    private static final String VERIFY_CODE_PREFIX = "emailAuthVerified:"; // 인증 완료된 이메일에 대한 접두사
    private static final long EMAIL_AUTH_TTL_MINUTES = 10; // 인증번호 유효시간 : 10분

    @Autowired
    public EmailAuthService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /* 설명. 이메일 인증 키 설정 */
    public void saveAuthCode(String email, String authCode) {
        String key = generateAuthKey(email);
        redisUtil.set(key, authCode, EMAIL_AUTH_TTL_MINUTES);
    }

    /* 설명. 이메일 인증 완료 됐다는 사실에 새로운 키 생성 */
    public void saveIsEmailVerfied(String email, Boolean verify) {
        String verifiedKey = generateVerifyKey(email);
        redisUtil.set(verifiedKey, verify.toString(), EMAIL_AUTH_TTL_MINUTES);
    }


    /* 설명. 이메일 인증번호 검증 */
    public boolean verifyAuthCode(String email, String authCode) {
        String key = generateAuthKey(email);
        String authCodeFromRedis = redisUtil.get(key);

        if (authCodeFromRedis == null) {
            return false;
        }
        return authCodeFromRedis.equals(authCode);
    }

    /* 설명. 이메일 인증번호 삭제 */
    public void deleteAuthCode(String email) {
        String key = generateAuthKey(email);
        redisUtil.delete(key);
    }

    /* 설명. 레디스에 저장할 키 이름 설정 */
    private String generateAuthKey(String email) {
        return EMAIL_AUTH_PREFIX + email;
    }

    private String generateVerifyKey(String email) {
        return VERIFY_CODE_PREFIX + email;
    }
}
