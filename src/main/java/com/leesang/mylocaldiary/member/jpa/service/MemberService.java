package com.leesang.mylocaldiary.member.jpa.service;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<CommonResponseVO<?>> reissueAccessToken(String request);

    void logout(String request);
}
