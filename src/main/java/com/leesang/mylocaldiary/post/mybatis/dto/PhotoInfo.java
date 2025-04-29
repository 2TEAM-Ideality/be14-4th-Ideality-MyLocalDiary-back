package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

@Data
public class PhotoInfo {
    private Long id;
    private String imageUrl;
    private Integer orders;
}