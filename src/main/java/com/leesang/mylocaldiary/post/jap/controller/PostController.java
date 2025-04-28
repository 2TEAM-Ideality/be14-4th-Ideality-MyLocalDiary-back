package com.leesang.mylocaldiary.post.jap.controller;

import com.leesang.mylocaldiary.member.entity.Member;
import com.leesang.mylocaldiary.member.repository.MemberRepository;
import com.leesang.mylocaldiary.post.jap.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jap.service.PostService;
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
    private final MemberRepository memberRepository; // ✅ 추가

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestPart PostCreateRequest request,
            @RequestPart List<MultipartFile> images,
            @RequestParam("memberId") Long memberId
    ) {
        // ✅ memberId로 Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        postService.createPost(request, images, member); // ✅ 이제 Member 넘긴다
        return ResponseEntity.ok().build();
    }
}