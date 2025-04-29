package com.leesang.mylocaldiary.auth.service;

import com.leesang.mylocaldiary.auth.dto.RequestEmailDTO;
import com.leesang.mylocaldiary.auth.dto.RequestSignUpDTO;
import com.leesang.mylocaldiary.auth.dto.RequestVerifyEmailDTO;
import com.leesang.mylocaldiary.email.service.EmailAuthService;
import com.leesang.mylocaldiary.email.service.EmailSendService;
import com.leesang.mylocaldiary.member.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final EmailAuthService emailAuthService;
    private final EmailSendService emailSendService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(EmailAuthService emailAuthService,
                           EmailSendService emailSendService,
                           MemberRepository memberRepository,
                           PasswordEncoder passwordEncoder) {
        this.emailAuthService = emailAuthService;
        this.emailSendService = emailSendService;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    @Transactional
    public int signUpMember(RequestSignUpDTO requestSignUpDTO) {
        boolean isEmailVerfiy = emailAuthService.verifyEmail(requestSignUpDTO.getEmail());

        if (!isEmailVerfiy) {
            return 0;
        }
        String encodedPassword = passwordEncoder.encode(requestSignUpDTO.getPassword());
        MemberEntity newMember = MemberEntity.builder()
                .loginId(requestSignUpDTO.getLoginId())
                .password(encodedPassword)
                .email(requestSignUpDTO.getEmail())
                .name(requestSignUpDTO.getName())
                .birth(requestSignUpDTO.getBirth())
                .nickname(requestSignUpDTO.getNickname())
                .isPublic(requestSignUpDTO.getIsPublic())
                .status("ACTIVE")
                .role("MEMBER")
                .build();

        memberRepository.save(newMember);
        emailAuthService.deleteVerified(requestSignUpDTO.getEmail());
        return 1;
    }

    private static String genereateRandomCode() {
        Random r = new Random();
        // 100000 ~ 999999
        return String.valueOf(r.nextInt(900000) + 100000);
    }
}
