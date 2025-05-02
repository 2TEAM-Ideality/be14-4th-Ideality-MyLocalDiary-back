package com.leesang.mylocaldiary.member.jpa.repository;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.aggregate.MemberProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<Object> findByLoginId(String loginId);

    Optional<MemberEntity> findByEmail(String userEmail);

    Optional<MemberEntity> findByProviderAndProviderId(MemberProvider provider, String providerId);

    boolean existsByLoginId(String nickname);

    boolean existsByNickname(String nickname);
}
