package com.leesang.mylocaldiary.post.jpa.controller;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jpa.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jpa.service.PostService;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil; // 🔥 JwtUtil 주입

    @PostMapping
    public ResponseEntity<?> createPost(
        @RequestPart PostCreateRequest request,
        @RequestPart List<MultipartFile> images,
        @RequestPart List<MultipartFile> thumbnails,
        @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Claims claims = jwtUtil.getClaimsAllowExpired(token); // 🔥 AllowExpired → 인증은 이미 끝난 상황
            Integer memberId = Integer.valueOf(claims.getSubject()); // 🔐 subject에 memberId 저장됨

            MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            postService.createPost(request, images, thumbnails, member);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("🔥 게시글 등록 중 에러 발생", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
// Pr 확인용 추가 문구