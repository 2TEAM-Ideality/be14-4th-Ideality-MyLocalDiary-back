package com.leesang.mylocaldiary.stamp.mybatis.service;

import com.leesang.mylocaldiary.stamp.mybatis.dto.MemberStampDTO;

import java.util.List;
import java.util.Map;

public interface StampQueryService {
    Map<String, Integer> findMemberStamp(Integer memberId);

    List<String> findBadgeByMemberId(Integer memberId);
}
