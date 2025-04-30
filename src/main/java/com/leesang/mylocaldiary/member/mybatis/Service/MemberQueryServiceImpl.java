package com.leesang.mylocaldiary.member.mybatis.Service;

import com.leesang.mylocaldiary.member.mybatis.Mapper.MemberMapper;
import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<SearchMemberDTO> searchMembers(String nickname, Long myId) {
        return memberMapper.searchMembersWithFollowStatus(nickname, myId);
    }
}
