package com.leesang.mylocaldiary.post.mybatis.controller;

import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mybatis/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;

    @GetMapping("/me")
    public ResponseEntity<List<PostResponse>> getMyPosts(@RequestParam int memberId) {
        return ResponseEntity.ok(postQueryService.getMyPosts(memberId));
    }

    @GetMapping("/my/{postId}")
    public ResponseEntity<PostResponse> getMyPostDetail(
            @PathVariable int postId,
            @RequestParam int memberId
    ) {
        PostResponse response = postQueryService.getMyPostDetail(postId, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/follow")
    public ResponseEntity<List<PostResponse>> getFollowedPosts(@RequestParam int memberId) {
        return ResponseEntity.ok(postQueryService.getFollowedPosts(memberId));
    }

    @GetMapping("/follow/{postId}")
    public ResponseEntity<PostDetailResponse> getFollowedPostDetail(@PathVariable int postId, @RequestParam int memberId) {
        return ResponseEntity.ok(postQueryService.getFollowedPostDetail(postId, memberId));
    }

    // 1. 좋아요 수 조회
    @GetMapping("/count")
    public int getLikeCount(@RequestParam int postId) {
        return postQueryService.getPostLikes(postId);
    }

    // 2. 특정 유저가 좋아요 눌렀는지 확인
    @GetMapping("/check")
    public boolean isLikedByUser(@RequestParam int postId, @RequestParam int memberId) {
        return postQueryService.isPostLikedByUser(postId, memberId);
    }
}