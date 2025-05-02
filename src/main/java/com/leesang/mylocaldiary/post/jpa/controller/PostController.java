package com.leesang.mylocaldiary.post.jpa.controller;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jpa.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jpa.service.LikeService;
import com.leesang.mylocaldiary.post.jpa.service.PostService;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private final LikeService likeService;
    private final JwtUtil jwtUtil; // 🔥 JwtUtil 주입

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestPart PostCreateRequest request,
            @RequestPart List<MultipartFile> images,
            @RequestPart List<MultipartFile> thumbnails,
            @RequestParam("memberId") Integer memberId
    ) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        postService.createPost(request, images, thumbnails, member);
        return ResponseEntity.ok().build();
    }

    /*
    // Security
    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestPart PostCreateRequest request,
            @RequestPart List<MultipartFile> images,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getId(); // 로그인한 사용자 ID
        postService.createPost(request, images, memberId);
        return ResponseEntity.ok().build();
    }
    */
}