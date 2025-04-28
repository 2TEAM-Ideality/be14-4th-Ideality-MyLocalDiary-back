package com.leesang.mylocaldiary.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestLoginDTO {
    private String loginId;
    private String password;
}
