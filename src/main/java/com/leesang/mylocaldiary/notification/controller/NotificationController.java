package com.leesang.mylocaldiary.notification.controller;

import com.leesang.mylocaldiary.notification.dto.NotificationResponseDTO;
import com.leesang.mylocaldiary.notification.entity.Notification;
import com.leesang.mylocaldiary.notification.service.NotificationService;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;


import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtProvider jwtProvider;

    public NotificationController(NotificationService notificationService, JwtProvider jwtProvider) {
        this.notificationService = notificationService;
        this.jwtProvider = jwtProvider;
    }

    @PatchMapping("/api/notifications/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }


    @GetMapping("/api/notifications")
    public List<NotificationResponseDTO> getMyNotifications(@RequestHeader("Authorization") String token) {
        String pureToken = token.replace("Bearer ", "");
        Long memberId = jwtProvider.getUserIdFromToken(pureToken);
        return notificationService.getNotificationsWithFollowStatus(memberId);
    }

}
