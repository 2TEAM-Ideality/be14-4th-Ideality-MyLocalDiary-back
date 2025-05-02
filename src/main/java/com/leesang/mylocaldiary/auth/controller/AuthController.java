package com.leesang.mylocaldiary.auth.controller;

import com.leesang.mylocaldiary.auth.dto.RequestEmailDTO;
import com.leesang.mylocaldiary.auth.dto.RequestSignUpDTO;
import com.leesang.mylocaldiary.auth.dto.RequestVerifyEmailDTO;
import com.leesang.mylocaldiary.auth.service.AuthService;
import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /* 설명. 포트 테스트용 */
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    /* 설명. 이메일 인증번호 발송 */
    @PostMapping("/email-verification-code")
    public ResponseEntity<CommonResponseVO<?>> emailVerificationCode(@RequestBody RequestEmailDTO requestEmailDTO) {
        authService.sendVerificationCode(requestEmailDTO);
        CommonResponseVO<Object> res = CommonResponseVO.builder()
                .status(200)
                .message("인증번호가 이메일로 전송되었습니다.")
                .data(null)
                .build();

        return ResponseEntity.ok(res);
    }

    /* 설명. 이메일 인증 */
    @PostMapping("/email-verification")
    public ResponseEntity<CommonResponseVO<?>> verifyEmail(@RequestBody RequestVerifyEmailDTO verifyDTO) {
        log.info(verifyDTO.getEmail());
        log.info(verifyDTO.getVerificationCode());

        authService.verificationCode(verifyDTO);

        CommonResponseVO<Object> res = CommonResponseVO.builder()
                .status(200)
                .message("인증이 완료되었습니다.")
                .data(null)
                .build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseVO<?>> signUp(@RequestBody RequestSignUpDTO requestSignUpDTO) {
        log.info(requestSignUpDTO.getBirth());
        int result = authService.signUpMember(requestSignUpDTO);
        CommonResponseVO<Object> res = CommonResponseVO.builder()
                .status(201)
                .message("회원가입이 완료되었습니다.")
                .data(null)
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/check-loginId")
    public ResponseEntity<CommonResponseVO<?>> checkLoginId(@RequestParam("loginId") String loginId) {
        boolean available = authService.isLoginIdAvaliable(loginId);
        return ResponseEntity.ok(
                CommonResponseVO.builder()
                        .status(200)
                        .message("사용 가능 여부 확인 완료")
                        .data(Map.of("available", available))
                        .build()
        );
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<CommonResponseVO<?>> checkNickname(@RequestParam("nickname") String nickname) {
        boolean available = authService.isNicknameAvaliable(nickname);
        return ResponseEntity.ok(
                CommonResponseVO.builder()
                        .status(200)
                        .message("사용 가능 여부 확인 완료")
                        .data(Map.of("available", available))
                        .build()
        );
    }
}
