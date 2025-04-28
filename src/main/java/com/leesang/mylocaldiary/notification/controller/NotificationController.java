package com.leesang.mylocaldiary.notification.controller;

import com.leesang.mylocaldiary.notification.entity.Notification;
import com.leesang.mylocaldiary.notification.service.NotificationService;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtProvider jwtProvider;

    public NotificationController(NotificationService notificationService, JwtProvider jwtProvider) {
        this.notificationService = notificationService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/api/notifications")
    public List<Notification> getMyNotifications(@RequestHeader("Authorization") String token) {
        String pureToken = token.replace("Bearer ", "");
        Long memberId = jwtProvider.getUserIdFromToken(pureToken);
        return notificationService.getNotifications(memberId);
    }
}
