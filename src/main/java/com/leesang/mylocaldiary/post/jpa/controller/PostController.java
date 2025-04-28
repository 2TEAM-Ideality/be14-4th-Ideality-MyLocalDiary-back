package com.leesang.mylocaldiary.post.jpa.controller;

import com.leesang.mylocaldiary.member.entity.Member;
import com.leesang.mylocaldiary.member.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jpa.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 시큐리티 적용할 때 필요
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import com.leesang.mylocaldiary.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository; // ✅ 추가

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestPart PostCreateRequest request,
            @RequestPart List<MultipartFile> images,
            @RequestParam("memberId") Long memberId
    ) {
        // memberId로 Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        postService.createPost(request, images, member);
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