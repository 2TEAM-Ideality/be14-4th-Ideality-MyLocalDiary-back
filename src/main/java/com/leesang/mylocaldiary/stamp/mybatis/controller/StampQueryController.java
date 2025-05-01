package com.leesang.mylocaldiary.stamp.mybatis.controller;

import com.leesang.mylocaldiary.stamp.mybatis.dto.MemberStampDTO;
import com.leesang.mylocaldiary.stamp.mybatis.service.StampQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StampQueryController {
    private final StampQueryService stampQueryService;

    @Autowired
    public StampQueryController(StampQueryService stampQueryService) {
        this.stampQueryService = stampQueryService;
    }

    // 멤버 id로 해당 멤버가 획득한 스탬프와 각 종류별 갯수
    @GetMapping("/stamp")
    public ResponseEntity<Map<String, Integer>> findMemberStamp(@RequestParam Integer memberId) {
        return ResponseEntity.ok(stampQueryService.findMemberStamp(memberId));
    }

    // 스탬프 id로 획득일 조회

    // 멤버 id로 해당 멤버가 획득한 뱃지 조회
    @GetMapping("/badge")
    public ResponseEntity<List<String>> findBadgesByMemberId(@RequestParam Integer memberId) {
        return ResponseEntity.ok(stampQueryService.findBadgeByMemberId(memberId));
    }
}
