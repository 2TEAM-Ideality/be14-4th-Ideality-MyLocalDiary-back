package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.Mapper.MemberMapper;
import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberInfoDTO findMemberInfo(Long memberId) {
        return memberMapper.selectMemberInfo(memberId);
    }

    @Override
    public OtherMemberInfoDTO findOtherMemberInfo(Long otherMemberId) {
        return memberMapper.selectOtherMemberInfo(otherMemberId);
    }
}
