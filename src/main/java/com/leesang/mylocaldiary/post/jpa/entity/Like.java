package com.leesang.mylocaldiary.post.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes", indexes = {
        @Index(name = "idx_user_target", columnList = "member_id, type, target_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private String createdAt; // 또는 LocalDateTime 으로 수정 권장

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;

    @Column(name = "target_id", nullable = false)
    private Integer targetId;

    public enum LikeType {
        POST, COMMENT
    }
}
