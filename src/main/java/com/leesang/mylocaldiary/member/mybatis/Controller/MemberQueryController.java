package com.leesang.mylocaldiary.member.mybatis.Controller;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import com.leesang.mylocaldiary.member.mybatis.Service.MemberQueryService;
import com.leesang.mylocaldiary.member.mybatis.dto.MemberInfoDTO;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberQueryController {

    private final MemberQueryService memberQueryService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberQueryController(MemberQueryService memberQueryService, JwtUtil jwtUtil) {
        this.memberQueryService = memberQueryService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/info")
    public ResponseEntity<CommonResponseVO<?>> memberInfo(HttpServletRequest request) {
        String token = jwtUtil.extractAccessToken(request);
        Long memberId = jwtUtil.getUserIdFromToken(token);
        MemberInfoDTO data = memberQueryService.findMemberInfo(memberId);
        return ResponseEntity.ok(CommonResponseVO.builder()
                        .status(200)
                        .message("")
                        .data(data)
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponseVO<?>> searchMembers(@RequestParam String nickname, @RequestParam Long myId) {
        return ResponseEntity.ok(CommonResponseVO.builder()
                .status(200)
                .message("검색 성공")
                .data(memberQueryService.searchMembers(nickname, myId)) // ✅ 여기 수정
                .build());
    }


}
