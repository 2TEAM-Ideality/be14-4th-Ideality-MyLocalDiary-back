package com.leesang.mylocaldiary.comment;

import com.leesang.mylocaldiary.comment.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest request) {
        commentService.createComment(request);
        return ResponseEntity.ok().build(); // 또는 created/return id도 가능
    }
}
