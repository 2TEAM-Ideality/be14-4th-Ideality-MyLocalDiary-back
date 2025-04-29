package com.leesang.mylocaldiary.auth.service;

import com.leesang.mylocaldiary.auth.dto.RequestEmailDTO;
import com.leesang.mylocaldiary.auth.dto.RequestVerifyEmailDTO;
import com.leesang.mylocaldiary.email.service.EmailAuthService;
import com.leesang.mylocaldiary.email.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final EmailAuthService emailAuthService;
    private final EmailSendService emailSendService;

    @Autowired
    public AuthServiceImpl(EmailAuthService emailAuthService, EmailSendService emailSendService) {
        this.emailAuthService = emailAuthService;
        this.emailSendService = emailSendService;
    }

    @Override
    public void sendVerificationCode(RequestEmailDTO requestEmailDTO) {
        String verificationCode = genereateRandomCode();
        emailAuthService.saveAuthCode(requestEmailDTO.getEmail(), verificationCode);
        emailSendService.sendEmailByVerification(requestEmailDTO.getEmail(), verificationCode);
    }

    @Override
    public void verificationCode(RequestVerifyEmailDTO verifyDTO) {
        boolean isVerify = emailAuthService.verifyAuthCode(verifyDTO.getEmail(),
                verifyDTO.getVerificationCode());

        if (isVerify) {
            emailAuthService.saveIsEmailVerfied(verifyDTO.getEmail(), true);
            emailAuthService.deleteAuthCode(verifyDTO.getEmail());
        }
    }

    private static String genereateRandomCode() {
        Random r = new Random();
        // 100000 ~ 999999
        return String.valueOf(r.nextInt(900000) + 100000);
    }
}
