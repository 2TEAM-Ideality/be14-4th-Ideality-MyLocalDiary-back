package com.leesang.mylocaldiary.stamp.jpa.repository;

import com.leesang.mylocaldiary.stamp.jpa.entity.MemberStamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStampRepository extends JpaRepository<MemberStamp, Long> {
    Long countByMemberIdAndStampId(Long targetMemberId, Long stampId);
}
