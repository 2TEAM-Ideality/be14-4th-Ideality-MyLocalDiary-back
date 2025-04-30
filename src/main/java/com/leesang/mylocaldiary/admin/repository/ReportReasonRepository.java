package com.leesang.mylocaldiary.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leesang.mylocaldiary.admin.aggregate.ReportEntity;
import com.leesang.mylocaldiary.admin.aggregate.ReportReasonEntity;

@Repository
public interface ReportReasonRepository extends JpaRepository<ReportReasonEntity, Integer> {
}
