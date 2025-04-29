package com.leesang.mylocaldiary.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    AUTH_CODE_EXPIRED(HttpStatus.UNAUTHORIZED, "인증 번호가 만료되었습니다."),
    AUTH_CODE_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "인증 번호가 일치하지 않습니다."),
    EMAIL_VERIFICATION_EXPIRED(HttpStatus.UNAUTHORIZED, "이메일 인증 시간이 만료되었습니다."),
    EMAIL_VERIFICATION_MISMATCH(HttpStatus.UNAUTHORIZED, "이메일 인증 코드가 일치하지 않습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, "인증되지 않는 이메일입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
