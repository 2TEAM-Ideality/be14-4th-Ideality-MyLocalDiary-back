package com.leesang.mylocaldiary.notification.service;

import com.leesang.mylocaldiary.follow.jpa.repository.FollowRepository;
import com.leesang.mylocaldiary.notification.controller.FollowSseController;
import com.leesang.mylocaldiary.notification.dto.NotificationResponseDTO;
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
    private final FollowRepository followRepository;

    public NotificationService(NotificationRepository notificationRepository, FollowSseController followSseController,
                               FollowRepository followRepository) {
        this.notificationRepository = notificationRepository;
        this.followSseController = followSseController;
        this.followRepository = followRepository;
    }
    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다."));
        notification.read(); // 상태 바꾸기
    }


    @Transactional
    public void sendFollowNotification(Long receiverId, Long followerId, String message) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notification notification = new Notification(
                "FOLLOW",
                followerId,
                message,  // ✅ 메시지를 그대로 사용
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

    public List<NotificationResponseDTO> getNotificationsWithFollowStatus(Long memberId) {
        List<Notification> notifications = notificationRepository.findByRecievingMemberIdOrderByIdDesc(memberId);

        return notifications.stream().map(noti -> {
            boolean isAccepted = false;

            // FOLLOW 알림이고, "요청"이라는 텍스트가 들어간 경우만 처리
            if ("FOLLOW".equals(noti.getType()) && noti.getContent().contains("요청")) {
                isAccepted = followRepository
                        .findByFollowingMemberIdAndFollowTargetMemberId(noti.getTargetId(), noti.getRecievingMemberId())
                        .map(f -> Boolean.TRUE.equals(f.getStatus())) // null-safe true 체크
                        .orElse(false);
            }

            return new NotificationResponseDTO(noti, isAccepted);
        }).toList();
    }

    @Transactional
    public void deleteFollowRequestNotification(Long senderId, Long receiverId) {
        List<Notification> notifications = notificationRepository.findByRecievingMemberIdOrderByIdDesc(receiverId);

        notifications.stream()
                .filter(n -> n.getType().equals("FOLLOW") &&
                        n.getTargetId().equals(senderId) &&
                        n.getContent().contains("요청"))
                .findFirst()
                .ifPresent(notificationRepository::delete);
    }


}
