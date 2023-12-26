package com.example.DevSculpt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {
    // 회원가입 시, 비밀번호 암호화 저장
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf(CsrfConfigurer::disable);
        http.
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/dev/**").permitAll()
                        .requestMatchers("/api/**").permitAll() // api테스트용 허용
                        .anyRequest().authenticated() // permitAll()처리한 요청을 제외한 것에 대해서는 인증을 받도록 처리
                ).formLogin(login -> login.defaultSuccessUrl("/main/index", true) // 성공 시 메인페이지로
                ).logout(Customizer.withDefaults());
        return http.build();
    }
}
