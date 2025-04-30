package com.leesang.mylocaldiary.admin.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leesang.mylocaldiary.admin.aggregate.SuspensionEntity;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;

@Repository
public interface SuspensionRepository extends JpaRepository<SuspensionEntity, Integer> {
	Optional<SuspensionEntity> findTopByMemberOrderBySuspensionEndDateDesc(MemberEntity member);

	List<SuspensionEntity> findBySuspensionEndDateBefore(LocalDateTime now);
}
