package com.leesang.mylocaldiary.post.jpa.repository;

import com.leesang.mylocaldiary.post.jpa.entity.Like;
import com.leesang.mylocaldiary.post.jpa.entity.Like.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    boolean existsByMemberIdAndTypeAndTargetId(Integer memberId, LikeType type, Integer targetId);

    void deleteByMemberIdAndTypeAndTargetId(Integer memberId, LikeType type, Integer targetId);

    int countByTypeAndTargetId(LikeType type, Integer targetId);
}
