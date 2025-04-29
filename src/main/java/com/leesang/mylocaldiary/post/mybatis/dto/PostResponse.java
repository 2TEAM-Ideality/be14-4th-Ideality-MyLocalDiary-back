package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostResponse {
    private Long postId;
    private String title;
    private String diary;
    private String createdAt;
    private String nickname;
    private String profileImage; // 작성자 프로필 이미지

    private Integer likeCount; // 좋아요 수
    private Boolean likedByCurrentUser; // 로그인 유저가 좋아요 눌렀는지

    private List<String> photoUrls; // 단순 URL 리스트
    private List<PhotoInfo> postList; // id, url 세트

    private List<PostPlaceInfo> places;
    private List<CommentResponse> comments;

    @Data
    public static class PostPlaceInfo {
        private String name;
        private Double latitude;
        private Double longitude;
    }

    @Data
    public static class PhotoInfo {
        private Long id;
        private String url;
    }

    @Data
    public static class CommentResponse {
        private Long id;
        private String username;
        private String avatar;
        private String text;
        private String createdAt;
        private Integer likeCount;
        private Boolean likedByCurrentUser;
    }
}
