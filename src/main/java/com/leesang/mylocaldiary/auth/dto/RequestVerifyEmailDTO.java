package com.leesang.mylocaldiary.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestVerifyEmailDTO {
    private String email;
    private String verificationCode;
}
