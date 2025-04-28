package com.leesang.mylocaldiary.security.config;

import com.leesang.mylocaldiary.security.filter.CustomAuthenticationFilter;
import com.leesang.mylocaldiary.security.filter.CustomAuthenticationProvider;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtProvider jwtProvider;

    @Autowired
    public WebSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, JwtProvider jwtProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, jwtProvider);
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login"); // 로그인 엔드포인트

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**"),
                                         new AntPathRequestMatcher("/api/posts/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(customAuthenticationFilter);

        return http.build();
    }
}