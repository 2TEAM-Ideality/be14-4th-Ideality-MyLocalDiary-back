package com.leesang.mylocaldiary.follow.jpa.controller;

import com.leesang.mylocaldiary.follow.jpa.dto.FollowRequestDTO;
import com.leesang.mylocaldiary.follow.jpa.entity.Follow;
import com.leesang.mylocaldiary.follow.jpa.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<?> follow(@RequestBody Follow follow) {
        followService.follow(follow.getFollowingMemberId(), follow.getFollowTargetMemberId(), follow.getStatus());
        return ResponseEntity.ok("팔로우 완료");
    }

    @DeleteMapping
    public ResponseEntity<?> unfollow(@RequestBody FollowRequestDTO followDto) {
        if (followDto == null || followDto.getFollowingMemberId() == null || followDto.getFollowTargetMemberId() == null) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        followService.unfollow(followDto.getFollowingMemberId(), followDto.getFollowTargetMemberId());
        return ResponseEntity.ok("언팔로우 완료");
    }

    @PatchMapping("/accept")
    public ResponseEntity<?> acceptFollow(@RequestBody FollowRequestDTO dto) {
        followService.acceptFollow(dto.getFollowingMemberId(), dto.getFollowTargetMemberId());
        return ResponseEntity.ok("팔로우 요청 수락 완료");
    }

    @DeleteMapping("/reject")
    public ResponseEntity<?> rejectFollowRequest(@RequestBody FollowRequestDTO dto) {
        followService.rejectFollow(dto.getFollowingMemberId(), dto.getFollowTargetMemberId());
        return ResponseEntity.ok("팔로우 요청 거절됨");
    }




}
