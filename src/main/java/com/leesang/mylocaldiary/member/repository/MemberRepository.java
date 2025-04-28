package com.leesang.mylocaldiary.member.repository;

import com.leesang.mylocaldiary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}