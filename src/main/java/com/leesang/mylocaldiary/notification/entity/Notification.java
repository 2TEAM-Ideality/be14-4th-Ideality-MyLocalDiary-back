package com.leesang.mylocaldiary.notification.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "created_at")
    private String createdAt;  // 보통은 LocalDateTime을 추천하지만 너 테이블 기준에 맞춰 String으로 씀

    @Column(name = "recieving_member_id", nullable = false)
    private Long recievingMemberId;

    public Notification(String type, Long targetId, String content, String createdAt, Long recievingMemberId) {
        this.type = type;
        this.targetId = targetId;
        this.content = content;
        this.createdAt = createdAt;
        this.recievingMemberId = recievingMemberId;
        this.isRead = false; // 생성 시 기본값
    }
    public void read() {
        this.isRead = true;
    }

}
