package com.leesang.mylocaldiary.member.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_id")
    private String loginId;


    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

<<<<<<< HEAD
    @Column(name = "name")
    private String name;

    @Column(name = "birth", nullable = false)
=======
    @Column(name = "birth")
>>>>>>> develop
    private String birth;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "deleted_at")
    private String deletedAt;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "status")
    private String status;

    @Builder.Default
    @Column(name = "suspension_count", nullable = false)
    private Integer suspensionCount = 0;

    @Column(name = "profile_music")
    private String profileMusic;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Builder.Default
    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "role")
    private String role;
}