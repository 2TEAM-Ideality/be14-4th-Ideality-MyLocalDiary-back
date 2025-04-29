package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

@Data
public class PlaceInfo {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String thumbnailImage;
    private Integer orders;
}