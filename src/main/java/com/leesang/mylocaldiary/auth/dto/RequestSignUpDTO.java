package com.leesang.mylocaldiary.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestSignUpDTO {
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String birth;
    private String nickname;
    private Boolean isPublic;
}
