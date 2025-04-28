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
    private List<String> photoUrls;  // 게시글에 등록된 사진들
    private List<PostPlaceInfo> places;  // 게시글에 등록된 장소들
}