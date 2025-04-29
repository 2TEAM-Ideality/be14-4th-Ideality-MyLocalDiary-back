package com.leesang.mylocaldiary.member.repository;

import com.leesang.mylocaldiary.member.aggregate.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<Object> findByLoginId(String loginId);

    Optional<MemberEntity> findByEmail(String userEmail);

    Optional<MemberEntity> findByProviderAndProviderId(String provider, String providerId);

}
