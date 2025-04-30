package com.leesang.mylocaldiary.notification.service;

import com.leesang.mylocaldiary.notification.controller.FollowSseController;
import com.leesang.mylocaldiary.notification.entity.Notification;
import com.leesang.mylocaldiary.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FollowSseController followSseController;

    public NotificationService(NotificationRepository notificationRepository, FollowSseController followSseController) {
        this.notificationRepository = notificationRepository;
        this.followSseController = followSseController;
    }
    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));
        notification.read(); // 상태 바꾸기
    }


    @Transactional
    public void sendFollowNotification(Long receiverId, Long followerId, String followerName) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notification notification = new Notification(
                "FOLLOW",
                followerId,
                followerName + "님이 당신을 팔로우했습니다!",
                now,
                receiverId
        );

        notificationRepository.save(notification);

        // 💡 Notification 객체를 그대로 SSE로 전송
        followSseController.sendFollowNotification(receiverId, notification);
    }


    public List<Notification> getNotifications(Long memberId) {
        return notificationRepository.findByRecievingMemberIdOrderByIdDesc(memberId);
    }
}
