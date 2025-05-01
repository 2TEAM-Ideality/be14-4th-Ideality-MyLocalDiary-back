package com.leesang.mylocaldiary.post.jpa.service;

import com.leesang.mylocaldiary.post.jpa.entity.Like;
import com.leesang.mylocaldiary.post.jpa.entity.Like.LikeType;
import com.leesang.mylocaldiary.post.jpa.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    @Transactional
    public void like(Integer postId, Integer memberId) {
        boolean alreadyLiked = likeRepository.existsByMemberIdAndTypeAndTargetId(memberId, LikeType.POST, postId);
        if (!alreadyLiked) {
            Like like = Like.builder()
                    .memberId(memberId)
                    .type(LikeType.POST)
                    .targetId(postId)
                    .createdAt(LocalDateTime.now().toString())
                    .build();
            likeRepository.save(like);
        }
    }

    @Transactional
    public void unlike(Integer postId, Integer memberId) {
        likeRepository.deleteByMemberIdAndTypeAndTargetId(memberId, LikeType.POST, postId);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Integer postId, Integer memberId) {
        return likeRepository.existsByMemberIdAndTypeAndTargetId(memberId, LikeType.POST, postId);
    }

    @Transactional(readOnly = true)
    public int countLikes(Integer postId) {
        return likeRepository.countByTypeAndTargetId(LikeType.POST, postId);
    }
}
