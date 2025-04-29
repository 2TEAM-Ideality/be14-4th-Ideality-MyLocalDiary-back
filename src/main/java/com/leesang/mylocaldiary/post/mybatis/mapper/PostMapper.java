package com.leesang.mylocaldiary.post.mybatis.mapper;

import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    // 1. 내가 쓴 게시글 전체 조회
    List<PostResponse> findMyPosts(@Param("memberId") Integer memberId);

    // 2. 내가 쓴 게시글 상세 조회
    PostDetailResponse findMyPostDetail(@Param("postId") Integer postId,
                                        @Param("memberId") Integer memberId);

    // 3. 팔로우한 유저의 게시글 전체 조회
    List<PostResponse> findFollowedPosts(@Param("memberId") Integer memberId);

    // 4. 팔로우한 유저의 게시글 상세 조회
    PostDetailResponse findFollowedPostDetail(@Param("postId") Integer postId,
                                              @Param("memberId") Integer memberId);

    // 5. 좋아요 수 조회
    int countPostLikes(@Param("postId") Integer postId);

    // 6. 특정 사용자가 게시글을 좋아요 눌렀는지 여부
    boolean isPostLikedByCurrentUser(@Param("postId") Integer postId,
                                     @Param("memberId") Integer memberId);
}
