package com.leesang.mylocaldiary.follow.controller;

import com.leesang.mylocaldiary.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public String follow(@RequestParam Long fromMemberId,
                         @RequestParam Long toMemberId,
                         @RequestParam String fromMemberName) {
        followService.followMember(fromMemberId, toMemberId, fromMemberName);
        return "팔로우 성공!";
    }
}
