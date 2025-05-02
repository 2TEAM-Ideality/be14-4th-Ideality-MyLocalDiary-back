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
    public void like(Integer targetId, Integer memberId, LikeType type) {
        boolean alreadyLiked = likeRepository.existsByMemberIdAndTypeAndTargetId(memberId, type, targetId);
        if (!alreadyLiked) {
            Like like = Like.builder()
                    .memberId(memberId)
                    .type(type)
                    .targetId(targetId)
                    .createdAt(LocalDateTime.now().toString())
                    .build();
            likeRepository.save(like);
        }
    }

    @Transactional
    public void unlike(Integer targetId, Integer memberId, LikeType type) {
        likeRepository.deleteByMemberIdAndTypeAndTargetId(memberId, type, targetId);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Integer targetId, Integer memberId, LikeType type) {
        return likeRepository.existsByMemberIdAndTypeAndTargetId(memberId, type, targetId);
    }

    @Transactional(readOnly = true)
    public int countLikes(Integer targetId, LikeType type) {
        return likeRepository.countByTypeAndTargetId(type, targetId);
    }
}

