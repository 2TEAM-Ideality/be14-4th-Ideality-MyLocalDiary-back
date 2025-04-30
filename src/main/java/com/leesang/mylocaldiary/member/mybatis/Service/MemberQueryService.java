package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;

public interface MemberQueryService {
    MemberInfoDTO findMemberInfo(Long memberId);

	// 테스트
	OtherMemberInfoDTO findOtherMemberInfo(Long loginMemberId, Long targetMemberId);
}
