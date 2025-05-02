package com.leesang.mylocaldiary.post.mybatis.service;

import com.leesang.mylocaldiary.post.mybatis.dto.CommentResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.MyPostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDateResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostSimpleResponse;
import com.leesang.mylocaldiary.post.mybatis.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostQueryService {

    private final PostMapper postMapper;

    // 1. 내가 쓴 게시글 전체 조회 (지도+마이페이지)
    public List<PostSimpleResponse> findMyPostsForMap(Integer memberId) {
        return postMapper.findMyPostsForMap(memberId);
    }

    // 1-1. 내가 쓴 게시글 전제 조회 (마이페이지/캘린더)
    public List<PostDateResponse> findMyPostsForCalendar(Integer memberId) {
        return postMapper.findMyPostsForCalendar(memberId);
    }

    // 2. 내가 쓴 게시글 상세 조회
    public MyPostDetailResponse findMyPostDetail(Integer postId, Integer memberId) {
        return postMapper.findMyPostDetail(postId, memberId);
    }

    // 3. 팔로우한 유저 게시글 전체 조회
    public List<PostSimpleResponse> findFollowedPostsForMap(Integer memberId) {
        return postMapper.findFollowedPostsForMap(memberId);
    }

    // 4. 팔로우한 유저 게시글 상세 조회
    public PostDetailResponse findFollowedPostDetail(Integer postId, Integer memberId) {
        return postMapper.findFollowedPostDetail(postId, memberId);
    }

    // 5. 게시글 좋아요 수 조회
    public int countPostLikes(Integer postId) {
        return postMapper.countPostLikes(postId);
    }

    // 6. 댓글 좋아요 수 조회
    public int countCommentLikes(Integer commentId) {
        return postMapper.countCommentLikes(commentId);
    }

    // 7. 게시글 좋아요 여부 확인
    public boolean isPostLikedByCurrentUser(Integer postId, Integer memberId) {
        return postMapper.isPostLikedByCurrentUser(postId, memberId);
    }

    // 8. 댓글 조회 (최상위 부모 댓글)
    public List<CommentResponse> findCommentsByPostId(Integer postId) {
        return postMapper.findCommentsByPostId(postId);
    }

    // 9. 대댓글 조회 (부모 댓글에 연결된)
    public List<CommentResponse> findRepliesByParentCommentId(Integer parentCommentId) {
        return postMapper.findRepliesByParentCommentId(parentCommentId);
    }

    // PostQueryService.java
    public boolean isCommentLikedByCurrentUser(Integer commentId, Integer memberId) {
        return postMapper.isCommentLikedByCurrentUser(commentId, memberId);
    }

}