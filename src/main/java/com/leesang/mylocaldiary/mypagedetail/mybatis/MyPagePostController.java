package com.leesang.mylocaldiary.mypagedetail.mybatis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPagePostController {

    private final MyPagePostService myPagePostService;

    @GetMapping("/{memberId}/posts/count")
    public int getPostCount(@PathVariable Long memberId) {
        return myPagePostService.getPostCountByMember(memberId);
    }
}