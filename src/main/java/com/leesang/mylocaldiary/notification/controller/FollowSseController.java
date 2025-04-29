package com.leesang.mylocaldiary.notification.controller;

import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class FollowSseController {

    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();
    private final JwtProvider jwtProvider;

    public FollowSseController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(value = "/api/follow/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam("token") String token) {
        Long userId = jwtProvider.getUserIdFromToken(token);

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.computeIfAbsent(userId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> emitters.get(userId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(userId).remove(emitter));

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("SSE 연결 완료"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emitter;
    }

    public void sendFollowNotification(Long targetUserId, String followerName) {
        List<SseEmitter> userEmitters = emitters.get(targetUserId);
        if (userEmitters != null) {
            for (SseEmitter emitter : new CopyOnWriteArrayList<>(userEmitters)) { // 복사본으로 안전하게
                try {
                    emitter.send(SseEmitter.event()
                            .name("follow")
                            .data(followerName + "님이 당신을 팔로우했습니다!"));
                } catch (IOException e) {
                    emitter.complete();
                    userEmitters.remove(emitter);
                }
            }
        }
    }
}
