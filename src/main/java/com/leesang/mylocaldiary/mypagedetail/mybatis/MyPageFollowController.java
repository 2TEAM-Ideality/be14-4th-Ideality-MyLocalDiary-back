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

    @GetMapping("/list")
    public List<FollowedUserDTO> getFollowingList(@RequestParam int memberId) {
        return myPageFollowService.getFollowingList(memberId);
    }
}