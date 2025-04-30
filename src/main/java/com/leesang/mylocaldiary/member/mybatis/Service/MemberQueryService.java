package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;

import java.util.List;

public interface MemberQueryService {
    MemberInfoDTO findMemberInfo(Long memberId);
    List<SearchMemberDTO> searchMembers(String nickname, Long myId);

}
