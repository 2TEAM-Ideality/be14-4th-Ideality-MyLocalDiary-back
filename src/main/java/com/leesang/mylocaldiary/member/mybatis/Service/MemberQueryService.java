package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;

import java.util.List;

public interface MemberQueryService {
    MemberInfoDTO findMemberInfo(Long memberId);

	OtherMemberInfoDTO findOtherMemberInfo(Long loginMemberId, Long targetMemberId);

    List<SearchMemberDTO> searchMembers(String nickname, Long myId);

}
