package com.leesang.mylocaldiary.stamp.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StampMapper {
    List<Map<String, Object>> selectMemberStamp(Integer memberId);
    List<String> selectBadgeByMemberId(Integer memberId);
}
