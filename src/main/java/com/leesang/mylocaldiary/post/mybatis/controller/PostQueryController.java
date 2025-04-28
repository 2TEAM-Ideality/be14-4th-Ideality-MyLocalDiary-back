package com.leesang.mylocaldiary.post.mybatis.controller;

import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import com.leesang.mylocaldiary.post.mybatis.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;

    // 1. 내가 쓴 게시글 전체 조회
    @GetMapping("/my")
    public ResponseEntity<List<PostResponse>> getMyPosts(@RequestParam Long memberId) {
        return ResponseEntity.ok(postQueryService.getMyPosts(memberId));
    }

    // 2. 내가 쓴 게시글 상세 조회
    @GetMapping("/my/{postId}")
    public ResponseEntity<PostResponse> getMyPostDetail(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(postQueryService.getMyPostDetail(postId, memberId));
    }

    // 3. 팔로우한 회원의 게시글 전체 조회
    @GetMapping("/following")
    public ResponseEntity<List<PostResponse>> getFollowedPosts(@RequestParam Long memberId) {
        return ResponseEntity.ok(postQueryService.getFollowedPosts(memberId));
    }

    // 4. 팔로우한 회원의 게시글 상세 조회
    @GetMapping("/following/{postId}")
    public ResponseEntity<PostResponse> getFollowedPostDetail(
            @PathVariable Long postId,
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(postQueryService.getFollowedPostDetail(postId, memberId));
    }
}