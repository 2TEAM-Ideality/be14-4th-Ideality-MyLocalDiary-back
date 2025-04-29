package com.leesang.mylocaldiary.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leesang.mylocaldiary.admin.aggregate.SuspensionEntity;

@Repository
public interface SuspensionRepository extends JpaRepository<SuspensionEntity, Integer> {
}
