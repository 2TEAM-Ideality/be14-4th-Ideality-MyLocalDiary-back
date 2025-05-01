package com.leesang.mylocaldiary.mypagedetail.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPagePostController {

    private final MyPagePostService myPagePostService;

    @GetMapping("/posts/count")
    public int getPostCount(@RequestParam Long memberId) {
        return myPagePostService.getPostCountByMember(memberId);
    }
}