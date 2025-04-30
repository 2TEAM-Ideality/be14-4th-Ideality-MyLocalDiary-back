package com.leesang.mylocaldiary.security.filter;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberEntity;
import com.leesang.mylocaldiary.member.jpa.repository.MemberRepository;
import com.leesang.mylocaldiary.security.details.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();              // 로그인 아이디
        String password = authentication.getCredentials().toString();  // 비밀번호

        log.info("로그인 인증 시도 - loginId: {}", loginId);

        // 1. DB에서 loginId로 회원 조회
        MemberEntity loginMember = (MemberEntity) memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BadCredentialsException("존재하지 않는 아이디입니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(password, loginMember.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 3. CustomUserDetails 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                loginMember.getId(),
                loginMember.getLoginId(),
                loginMember.getPassword(),
                loginMember.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + loginMember.getMemberRole())),
                loginMember.getIsDeleted(),
                loginMember.getStatus()
        );

        // 4. 인증 완료 -> UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
