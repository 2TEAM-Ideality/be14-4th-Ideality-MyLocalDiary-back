package com.leesang.mylocaldiary.post.jpa.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostCreateRequest {
    private String title;
    private String post;
    private String diary;
    private List<String> imageUrls;
    private List<PlaceDto> places;

    @Getter
    public static class PlaceDto {
        private String name;
        private Double latitude;
        private Double longitude;
        private Integer orders;
        private String thumbnailImage;
    }
}