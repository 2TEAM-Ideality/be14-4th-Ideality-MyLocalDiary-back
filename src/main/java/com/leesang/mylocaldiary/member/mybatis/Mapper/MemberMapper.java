package com.leesang.mylocaldiary.member.mybatis.Mapper;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberInfoDTO selectMemberInfo(Long memberId);

    List<SearchMemberDTO> searchMembersWithFollowStatus(@Param("nickname") String nickname, @Param("myId") Long myId);
}