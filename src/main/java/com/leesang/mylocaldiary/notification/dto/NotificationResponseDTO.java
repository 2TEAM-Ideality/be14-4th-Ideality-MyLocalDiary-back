package com.leesang.mylocaldiary.notification.dto;


import com.leesang.mylocaldiary.notification.entity.Notification;
import lombok.Getter;

@Getter
public class NotificationResponseDTO {
    private Long id;
    private String type;
    private Long targetId;
    private String content;
    private boolean isRead;
    private String createdAt;
    private boolean isAccepted; // ✅ 수락 여부 추가

    public NotificationResponseDTO(Notification n, boolean isAccepted) {
        this.id = n.getId();
        this.type = n.getType();
        this.targetId = n.getTargetId();
        this.content = n.getContent();
        this.isRead = n.getIsRead();
        this.createdAt = n.getCreatedAt();
        this.isAccepted = isAccepted;
    }
}