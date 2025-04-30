package com.leesang.mylocaldiary.member.mybatis.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberInfoDTO {

    private Integer memberId;
    private String loginId;
    private String email;
    private String nickname;
    private String bio;
    private String profileImage;
    private String profileMusic;
    private String status;
    private String role;
    private Boolean isPublic;
}
