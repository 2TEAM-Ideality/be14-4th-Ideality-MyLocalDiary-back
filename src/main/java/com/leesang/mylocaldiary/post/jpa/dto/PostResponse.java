package com.leesang.mylocaldiary.post.jpa.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponse {

    private Integer postId;
    private String title;
    private String diary;
    private String post;
    private String createdAt;
    private List<PhotoDto> photos;
    private List<PlaceDto> places;

    @Getter
    @Builder
    public static class PhotoDto {
        private Integer id;
        private String imageUrl;
        private Integer orders;
    }

    @Getter
    @Builder
    public static class PlaceDto {
        private Integer id;
        private String name;
        private Double latitude;
        private Double longitude;
        private Integer orders;
        private String thumbnailImage;
    }

    @Builder
    public PostResponse(Integer postId, String title, String diary, String post, String createdAt, List<PhotoDto> photos, List<PlaceDto> places) {
        this.postId = postId;
        this.title = title;
        this.diary = diary;
        this.post = post;
        this.createdAt = createdAt;
        this.photos = photos;
        this.places = places;
    }
}