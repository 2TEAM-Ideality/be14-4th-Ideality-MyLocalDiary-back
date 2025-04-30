package com.leesang.mylocaldiary.member.service;

import com.leesang.mylocaldiary.common.response.CommonResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<CommonResponseVO<?>> reissueAccessToken(HttpServletRequest request);

    void logout(HttpServletRequest request);
}
