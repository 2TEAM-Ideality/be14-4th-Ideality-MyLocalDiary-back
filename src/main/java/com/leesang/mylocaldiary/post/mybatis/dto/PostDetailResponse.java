package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostDetailResponse {
    private Long postId;    //
    private String title;   //
    private String post;
    private String diary;
    private String createdAt;
    private Integer memberId;
    private String nickname;
    private String profileImage;
    private List<PhotoInfo> photos;
    private List<PlaceInfo> places;
}