package com.leesang.mylocaldiary.post.mybatis.dto;

import lombok.Data;
import java.util.List;

@Data
public class MyPostDetailResponse {
    private Long postId;    //
    private String title;   //
    private String post;
    private String diary;
    private String createdAt;
    private Integer memberId;
    private String nickname;
    private Integer likes;
    private String profileImage;
    private Integer postLikeCount;
    private List<PhotoInfo> photos;
    private List<PlaceInfo> places;
}