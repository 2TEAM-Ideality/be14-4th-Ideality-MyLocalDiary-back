package com.leesang.mylocaldiary.post.jap.controller;

import com.leesang.mylocaldiary.post.jap.dto.PostCreateRequest;
import com.leesang.mylocaldiary.post.jap.dto.PostResponse;
import com.leesang.mylocaldiary.post.jap.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest request) {
        PostResponse response = postService.savePost(request);
        return ResponseEntity.ok(response);
    }
}