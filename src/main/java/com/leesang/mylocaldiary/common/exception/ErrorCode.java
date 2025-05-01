package com.leesang.mylocaldiary.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 회원가입
    AUTH_CODE_EXPIRED(HttpStatus.UNAUTHORIZED, "인증 번호가 만료되었습니다."),
    AUTH_CODE_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "인증 번호가 일치하지 않습니다."),
    EMAIL_VERIFICATION_EXPIRED(HttpStatus.UNAUTHORIZED, "이메일 인증 시간이 만료되었습니다."),
    EMAIL_VERIFICATION_MISMATCH(HttpStatus.UNAUTHORIZED, "이메일 인증 코드가 일치하지 않습니다."),
    EMAIL_NOT_VERIFIED(HttpStatus.UNAUTHORIZED, "인증되지 않는 이메일입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),

    // JWT (access, refresh)
    REFRESH_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다. 재로그인 하십시오."),
    TOKEN_NOT_EQUALS(HttpStatus.UNAUTHORIZED, "토큰이 일치하지 않습니다. 다시 로그인 해주시길 바랍니다."),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "허용되지 않은 접근입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않는 토큰입니다."),

    // ADMIN
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 신고 내역을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    ALREADY_SUSPENDED(HttpStatus.CONFLICT, "이미 정지된 회원입니다."),
    SUSPENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "정지 기록이 존재하지 않습니다."),
    REPORT_REASON_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정지 사유가 존재하지 않습니다." ),

    // POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다." );

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
