package com.leesang.mylocaldiary.member.mybatis.Mapper;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberInfoDTO selectMemberInfo(Long memberId);

    OtherMemberInfoDTO selectOtherMemberInfo(Long memberId);

}