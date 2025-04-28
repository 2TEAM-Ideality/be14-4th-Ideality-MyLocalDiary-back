package com.leesang.mylocaldiary.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private String email;

    private String nickname;

    private boolean isPublic;

    private boolean isDeleted;

    // 추가로 필요한 필드 있으면 채워넣기
}