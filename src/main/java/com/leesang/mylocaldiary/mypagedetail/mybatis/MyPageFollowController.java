package com.leesang.mylocaldiary.mypagedetail.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage/follow")
@RequiredArgsConstructor
public class MyPageFollowController {

    private final MyPageFollowService myPageFollowService;

    @GetMapping("/count")
    public int getFollowingCount(@RequestParam int memberId) {
        return myPageFollowService.getFollowingCount(memberId);
    }

    // 팔로워 수 (🔵 추가)
    @GetMapping("/count/follower")
    public int getFollowerCount(@RequestParam int memberId) {
        return myPageFollowService.getFollowerCount(memberId);
    }

    @GetMapping("/list")
    public List<FollowedUserDTO> getFollowingList(@RequestParam int memberId) {
        return myPageFollowService.getFollowingList(memberId);
    }

    // 팔로워 목록 (🔵 추가)
    @GetMapping("/list/follower")
    public List<FollowedUserDTO> getFollowerList(@RequestParam int memberId) {
        return myPageFollowService.getFollowerList(memberId);
    }



}