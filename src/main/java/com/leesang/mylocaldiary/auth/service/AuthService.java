package com.leesang.mylocaldiary.auth.service;

import com.leesang.mylocaldiary.auth.dto.RequestEmailDTO;
import com.leesang.mylocaldiary.auth.dto.RequestSignUpDTO;
import com.leesang.mylocaldiary.auth.dto.RequestVerifyEmailDTO;

public interface AuthService {
    void sendVerificationCode(RequestEmailDTO requestEmailDTO);

    void verificationCode(RequestVerifyEmailDTO verifyDTO);

    int signUpMember(RequestSignUpDTO requestSignUpDTO);

    boolean isLoginIdAvaliable(String loginId);

    boolean isNicknameAvaliable(String nickname);
}
