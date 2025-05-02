package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;

@Data
public class PostSimpleResponse {
    private Long postId;
    private Long memberId;
    private String postTitle;   // 게시글 제목 추가
    private String createdAt;   // 게시글 날짜 추가
    private String placeName;
    private Double latitude;
    private Double longitude;
    private String thumbnailImage;
}