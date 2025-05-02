package com.leesang.mylocaldiary.comment.dto;

public record CommentRequest(
        Long postId,
        Integer memberId,
        Integer parentCommentId,   // optional
        Integer targetMemberId,    // optional, for mention
        String content
) {}

