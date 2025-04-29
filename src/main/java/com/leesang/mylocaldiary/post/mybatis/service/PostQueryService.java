package com.leesang.mylocaldiary.post.mybatis.service;

import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import com.leesang.mylocaldiary.post.mybatis.dto.PostDetailResponse;
import com.leesang.mylocaldiary.post.mybatis.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostQueryService {

    private final PostMapper postMapper;

    public List<PostResponse> getMyPosts(int memberId) {
        return postMapper.findMyPosts(memberId);
    }

    public PostResponse getMyPostDetail(int postId, int memberId) {
        return postMapper.findMyPostDetail(postId, memberId);  // 캐스팅 제거
    }

    public List<PostResponse> getFollowedPosts(int memberId) {
        return postMapper.findFollowedPosts(memberId);
    }

    public PostDetailResponse getFollowedPostDetail(int postId, int memberId) {
        PostDetailResponse detail = postMapper.findFollowedPostDetail(postId, memberId);
        enrichDetail(detail, postId, memberId);
        return detail;
    }

    private void enrichDetail(PostDetailResponse detail, int postId, int memberId) {
        detail.setPostLikeCount(postMapper.countPostLikes(postId));
        detail.setPostLikedByCurrentUser(postMapper.isPostLikedByCurrentUser(postId, memberId));
    }

    public int getPostLikes(int postId) {
        return postMapper.countPostLikes(postId);
    }

    public boolean isPostLikedByUser(int postId, int memberId) {
        return postMapper.isPostLikedByCurrentUser(postId, memberId);
    }
}