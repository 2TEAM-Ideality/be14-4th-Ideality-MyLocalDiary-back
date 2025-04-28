package com.leesang.mylocaldiary.auth.controller;

import com.leesang.mylocaldiary.auth.dto.RequestLoginDTO;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private CommonResponseVO commonResponseVO;

    /* 설명. 포트 테스트용 */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

}
