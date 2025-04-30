package com.leesang.mylocaldiary.security.details;

import com.leesang.mylocaldiary.member.jpa.aggregate.MemberStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Integer memberId;
    private final String loginId;
    private final String password;
    @Getter
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities; // 🔥 권한
    private final Boolean isDeleted;
    private final MemberStatus status;

    public CustomUserDetails(Integer memberId,
                             String loginId,
                             String password,
                             String email,
                             Collection<? extends GrantedAuthority> authorities,
                             Boolean isDeleted,
                             MemberStatus status) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.isDeleted = isDeleted;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;  // 🔥 탈퇴한 회원은 로그인 불가
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.equals("SUSPENDED"); // 🔥 정지된 회원은 로그인 불가
    }

}
