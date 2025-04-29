package com.leesang.mylocaldiary.security.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private int status;
    private String message;
    private TokenData data;

    @Data
    @AllArgsConstructor
    public static class TokenData {
        private String accessToken;
    }
}
