package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String post;
    private String diary;
    private String createdAt;
    private int postLikeCount;
    private boolean postLikedByCurrentUser;
    private AuthorInfo author;
    private List<PhotoInfo> postList;
    private List<CommentInfo> comments;

    @Data
    public static class AuthorInfo {
        private String name;
        private String avatar;
    }

    @Data
    public static class PhotoInfo {
        private Long id;
        private String url;
    }

    @Data
    public static class CommentInfo {
        private Long id;
        private String username;
        private String avatar;
        private String text;
        private String createdAt;
        private int likeCount;
        private boolean likeByCurrentUser;
    }
}
