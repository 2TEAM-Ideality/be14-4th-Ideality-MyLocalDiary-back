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
        // 날짜 포맷팅
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 1. DB에 저장
        Notification notification = new Notification(
                "FOLLOW",             // type
                followerId,            // targetId (팔로우한 사람 ID)
                followerName + "님이 당신을 팔로우했습니다!", // content
                now,                   // createdAt
                receiverId             // recievingMemberId
        );
        notificationRepository.save(notification);

        // 2. SSE로 실시간 알림 보내기
        followSseController.sendFollowNotification(receiverId, followerName);
    }

    public List<Notification> getNotifications(Long memberId) {
        return notificationRepository.findByRecievingMemberIdOrderByIdDesc(memberId);
    }
}
