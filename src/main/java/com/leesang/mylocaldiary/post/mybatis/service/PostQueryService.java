package com.leesang.mylocaldiary.post.mybatis.service;

import com.leesang.mylocaldiary.post.mybatis.dto.PostResponse;
import com.leesang.mylocaldiary.post.mybatis.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostQueryService {

    private final PostMapper postMapper;

    // 1. 내가 쓴 게시글 전체 조회
    public List<PostResponse> getMyPosts(Long memberId) {
        return postMapper.findMyPosts(memberId);
    }

    // 2. 내가 쓴 게시글 상세 조회
    public PostResponse getMyPostDetail(Long postId, Long memberId) {
        return postMapper.findMyPostDetail(postId, memberId);
    }

    // 3. 팔로우한 회원들의 게시글 전체 조회
    public List<PostResponse> getFollowedPosts(Long memberId) {
        return postMapper.findFollowedPosts(memberId);
    }

    // 4. 팔로우한 회원들의 게시글 상세 조회
    public PostResponse getFollowedPostDetail(Long postId, Long memberId) {
        return postMapper.findFollowedPostDetail(postId, memberId);
    }
}