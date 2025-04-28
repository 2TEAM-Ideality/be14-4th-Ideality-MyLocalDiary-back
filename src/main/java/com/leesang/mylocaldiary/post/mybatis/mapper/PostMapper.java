package com.leesang.mylocaldiary.post.mybatis.mapper;

import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    // 1. 내가 쓴 게시글 전체 조회
    List<PostResponse> findMyPosts(@Param("memberId") Long memberId);

    // 2. 내가 쓴 게시글 상세 조회
    PostResponse findMyPostDetail(@Param("postId") Long postId, @Param("memberId") Long memberId);

    // 3. 팔로우한 회원의 게시글 전체 조회
    List<PostResponse> findFollowedPosts(@Param("memberId") Long memberId);

    // 4. 팔로우한 회원의 게시글 상세 조회
    PostResponse findFollowedPostDetail(@Param("postId") Long postId, @Param("memberId") Long memberId);
}