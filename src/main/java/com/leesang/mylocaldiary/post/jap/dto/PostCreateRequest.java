package com.leesang.mylocaldiary.post.jap.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    private String title;
    private String diary;
    private String post;
    private List<PhotoRequest> photos;
    private List<PlaceRequest> places;
    private Integer memberId; // 회원 식별자

    @Getter
    @NoArgsConstructor
    public static class PhotoRequest {
        private String imageUrl;
        private Integer orders;
    }

    @Getter
    @NoArgsConstructor
    public static class PlaceRequest {
        private String name;
        private Double latitude;
        private Double longitude;
        private Integer orders;
        private String thumbnailImage;
    }
}