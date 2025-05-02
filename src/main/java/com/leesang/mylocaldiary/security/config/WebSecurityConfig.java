package com.leesang.mylocaldiary.security.config;

import com.leesang.mylocaldiary.redis.util.RedisUtil;
import com.leesang.mylocaldiary.security.filter.CustomAuthenticationFilter;
import com.leesang.mylocaldiary.security.jwt.JwtFilter;
import com.leesang.mylocaldiary.security.handler.JwtAuthenticationEntryPoint;
import com.leesang.mylocaldiary.security.jwt.JwtProvider;
import com.leesang.mylocaldiary.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final RedisTemplate<String, String> redisTemplate;  // 🔥 RedisTemplate 주입 추가
    private final RedisUtil redisUtil;

    @Autowired
    public WebSecurityConfig(JwtProvider jwtProvider,
                             JwtUtil jwtUtil,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             RedisTemplate<String, String> redisTemplate,
                             RedisUtil redisUtil) {
        this.jwtProvider = jwtProvider;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.redisTemplate = redisTemplate;
        this.redisUtil = redisUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManager, jwtProvider, redisTemplate);
        customAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/api/auth/login", "POST")
        );
        JwtFilter jwtFilter = new JwtFilter(jwtUtil, redisUtil);
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/follow/**")).permitAll() // follow 허용
                        .requestMatchers(new AntPathRequestMatcher("/api/notifications/**")).permitAll() // notifications 허용
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).permitAll() // admin 허용
                        .requestMatchers(new AntPathRequestMatcher("/login/kakao")).permitAll() // kakao 로그인 허용
                        .requestMatchers(new AntPathRequestMatcher("/callback")).permitAll() // kakao callback 허용
                        .requestMatchers("/api/naver/search").permitAll()  // 지도 api 허용
                        .requestMatchers(new AntPathRequestMatcher("/api/posts/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/stamp/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/badge")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/mypage/**")).permitAll()
                        .requestMatchers("/api/member/reissue").permitAll()
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
                            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                            config.setAllowedHeaders(Arrays.asList("*"));
                            config.setAllowCredentials(true);
                            return config;
                        })
                )
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}