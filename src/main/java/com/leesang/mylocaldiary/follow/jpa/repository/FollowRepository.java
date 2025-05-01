package com.leesang.mylocaldiary.follow.jpa.repository;

import com.leesang.mylocaldiary.follow.jpa.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingMemberIdAndFollowTargetMemberId(Long from, Long to);
    void deleteByFollowingMemberIdAndFollowTargetMemberId(Long from, Long to);

    boolean existsByFollowingMemberIdAndFollowTargetMemberId(Long from, Long to); // ✅ 추가
}
