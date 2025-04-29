package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private String createdAt;
    private Integer likeCount;
    private boolean likedByCurrentUser;
    private Integer memberId;
    private String nickname;
    private String profileImage;
}