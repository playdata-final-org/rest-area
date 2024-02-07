package com.example.DevSculpt.config;


import com.example.DevSculpt.service.alarm.SseEmitterService;
import com.example.DevSculpt.service.security.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final SseEmitterService sseEmitterService;

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
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/upload/**").permitAll()
                        .requestMatchers("/sse/**").permitAll()
                        .requestMatchers("/api/**").permitAll() // api테스트용 허용
                        .anyRequest().authenticated() // permitAll()처리한 요청을 제외한 것에 대해서는 인증을 받도록 처리
                )
                .formLogin(login -> login
                        .loginPage("/dev/login")
                        .permitAll()
                        .successHandler(customAuthenticationSuccessHandler)
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/dev/login")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers(
                            PathRequest.toStaticResources().atCommonLocations()
                    );
        };
    }

    @Bean
    public SecurityEventListener mySecurityEventListener() {
        return new SecurityEventListener(sseEmitterService);  // SseEmitterService 주입
    }
}

