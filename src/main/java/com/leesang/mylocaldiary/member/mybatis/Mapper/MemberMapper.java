package com.leesang.mylocaldiary.member.mybatis.Mapper;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberInfoDTO selectMemberInfo(Long memberId);
}