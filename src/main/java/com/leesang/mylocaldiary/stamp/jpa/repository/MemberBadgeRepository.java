package com.leesang.mylocaldiary.stamp.jpa.repository;

import com.leesang.mylocaldiary.stamp.jpa.entity.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
    boolean existsByMemberIdAndBadgeId(Long targetMemberId, Long stampId);
}
