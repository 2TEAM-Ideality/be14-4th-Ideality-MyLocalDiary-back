package com.leesang.mylocaldiary.member.mybatis.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.leesang.mylocaldiary.member.mybatis.Mapper.MemberMapper;
import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.OtherMemberInfoDTO;
import com.leesang.mylocaldiary.member.mybatis.dto.SearchMemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public OtherMemberInfoDTO findOtherMemberInfo(Long loginMemberId, Long targetMemberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginMemberId", loginMemberId);
        params.put("targetMemberId", targetMemberId);
        log.info("findOtherMemberInfo:{}", params);
        return memberMapper.selectOtherMemberInfo(params);
    }


    @Override
    public List<SearchMemberDTO> searchMembers(String nickname, Long myId) {
        return memberMapper.searchMembersWithFollowStatus(nickname, myId);
    }
}
