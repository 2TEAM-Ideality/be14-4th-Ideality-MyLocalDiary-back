package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String post;
    private String createdAt;
    private Integer memberId;
    private String nickname;
    private String profileImage;
    private List<PhotoInfo> photos;
    private List<PlaceInfo> places;

    @Data
    public static class PhotoInfo {
        private Long id;
        private String imageUrl;
        private Integer orders;
    }

    @Data
    public static class PlaceInfo {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private String thumbnailImage;
        private Integer orders;
    }
}