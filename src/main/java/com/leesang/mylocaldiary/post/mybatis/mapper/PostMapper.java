package com.leesang.mylocaldiary.post.mybatis.mapper;

import com.leesang.mylocaldiary.post.mybatis.dto.CommentResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.MyPostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDateResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostSimpleResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    List<PostSimpleResponse> findMyPostsForMap(@Param("memberId") Integer memberId);

    List<PostDateResponse> findMyPostsForCalendar(Integer memberId);

    MyPostDetailResponse findMyPostDetail(@Param("postId") Integer postId, @Param("memberId") Integer memberId);

    List<PostSimpleResponse> findFollowedPostsForMap(@Param("memberId") Integer memberId);

    PostDetailResponse findFollowedPostDetail(@Param("postId") Integer postId, @Param("memberId") Integer memberId);

    int countPostLikes(@Param("postId") Integer postId);

    int countCommentLikes(@Param("commentId") Integer commentId);

    boolean isPostLikedByCurrentUser(@Param("postId") Integer postId, @Param("memberId") Integer memberId);

    List<CommentResponse> findCommentsByPostId(@Param("postId") Integer postId);

    List<CommentResponse> findRepliesByParentCommentId(@Param("parentCommentId") Integer parentCommentId);

    boolean isCommentLikedByCurrentUser(@Param("commentId") Integer commentId, @Param("memberId") Integer memberId);

}