package com.leesang.mylocaldiary.member.mybatis.Mapper;

import java.util.Map;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberInfoDTO selectMemberInfo(Long memberId);

    OtherMemberInfoDTO selectOtherMemberInfo(Map<String, Object> params);

}