package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;

public interface MemberQueryService {
    MemberInfoDTO findMemberInfo(Long memberId);
}
