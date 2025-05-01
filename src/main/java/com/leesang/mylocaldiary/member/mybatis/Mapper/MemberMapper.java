package com.leesang.mylocaldiary.member.mybatis.Mapper;

import java.util.Map;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberInfoDTO selectMemberInfo(Long memberId);

    OtherMemberInfoDTO selectOtherMemberInfo(Map<String, Object> params);

    List<SearchMemberDTO> searchMembersWithFollowStatus(@Param("nickname") String nickname, @Param("myId") Long myId);
}