package com.leesang.mylocaldiary.auth.service;

import com.leesang.mylocaldiary.auth.dto.RequestEmailDTO;

public interface AuthService {
    void sendVerificationCode(RequestEmailDTO requestEmailDTO);
}
