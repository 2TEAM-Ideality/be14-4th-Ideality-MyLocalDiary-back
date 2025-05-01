package com.leesang.mylocaldiary.mypagedetail.mybatis;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPagePostService {

    private final MyPagePostMapper myPagePostMapper;

    public int getPostCountByMember(Long memberId) {
        return myPagePostMapper.countPostsByMemberId(memberId);
    }
}