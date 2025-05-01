package com.leesang.mylocaldiary.stamp.jpa.controller;

import com.leesang.mylocaldiary.stamp.jpa.dto.RegisterStampDTO;
import com.leesang.mylocaldiary.stamp.jpa.service.StampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/stamp")
public class StampController {

    private final StampService stampService;

    @Autowired
    public StampController(StampService stampService) {
        this.stampService = stampService;
    }
    
    // 스탬프 획득
    @PostMapping("/achieved")
    public ResponseEntity<?> registStamp(@RequestBody RegisterStampDTO registStamp) {
        stampService.registStamp(registStamp.getTargetMemberId(), registStamp.getStampId());
        return ResponseEntity.ok("스탬프 획득");
    }
}
