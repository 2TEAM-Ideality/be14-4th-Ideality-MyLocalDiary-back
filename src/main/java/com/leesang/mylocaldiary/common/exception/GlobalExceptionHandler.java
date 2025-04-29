package com.leesang.mylocaldiary.common.exception;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<CommonResponseVO<?>> handleGlobalException(GlobalException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(CommonResponseVO.builder()
                        .status(errorCode.getStatus().value())
                        .message(errorCode.getMessage())
                        .data(null)
                        .build());
    }

    // 혹시 모를 예상치 못한 에러 대응
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseVO<?>> handleException(Exception e) {
        return ResponseEntity
                .status(500)
                .body(CommonResponseVO.builder()
                        .status(500)
                        .message("서버 내부 오류가 발생했습니다.")
                        .data(null)
                        .build());
    }
}