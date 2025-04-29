package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

@Data
public class PostSimpleResponse {
    private Long postId;
    private String placeName;
    private Double latitude;
    private Double longitude;
    private String thumbnailImage;
}