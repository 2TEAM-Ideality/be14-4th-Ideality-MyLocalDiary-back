package com.leesang.mylocaldiary.follow.service;

import com.leesang.mylocaldiary.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final NotificationService notificationService;

    public void followMember(Long fromMemberId, Long toMemberId, String fromMemberName) {
        // 1. (팔로우 테이블 저장하는 로직은 생략 - 필요하면 추가)

        // 2. 알림 저장 + SSE 알림 푸시
        notificationService.sendFollowNotification(toMemberId, fromMemberId, fromMemberName);
    }
}
