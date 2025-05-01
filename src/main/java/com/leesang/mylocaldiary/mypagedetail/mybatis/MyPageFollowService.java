package com.leesang.mylocaldiary.mypagedetail.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageFollowService {

    private final MyPageFollowMapper myPageFollowMapper;

    public int getFollowingCount(int memberId) {
        return myPageFollowMapper.countFollowing(memberId);
    }

    public List<FollowedUserDTO> getFollowingList(int memberId) {
        return myPageFollowMapper.getFollowingList(memberId);
    }

    public int getFollowerCount(int memberId) { // 🔵 추가
        return myPageFollowMapper.getFollowerCount(memberId);
    }

    public List<FollowedUserDTO> getFollowerList(int memberId) { // 🔵 추가
        return myPageFollowMapper.getFollowerList(memberId);
    }


}