package com.example.BAS.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true) //시큐리티 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        //패스워드 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf비활성화
        http.csrf(CsrfConfigurer::disable);
        // 권한설정
        http.authorizeHttpRequests(request -> {
            request.requestMatchers(
                            new AntPathRequestMatcher("/**"),
                            new AntPathRequestMatcher("/main"),
                            new AntPathRequestMatcher("/signin"),
                            new AntPathRequestMatcher("/signup"),
                            new AntPathRequestMatcher("/static/**")
                    )
                    .permitAll()
                    .anyRequest().authenticated();
        });
        // 폼 로그인
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/signin") //인증이 필요한 주소로 접속하면 이 주소로 이동시킴(Get)
                        .permitAll()
                        .loginProcessingUrl("/signin")//스프링 시큐리티가 로그인 자동 진행(Post)
                        .defaultSuccessUrl("/test")//로그인 성공 후 이동할 페이지
                        .failureUrl("/signin?error=true") //로그인 실패시 이동할 페이지
        );
        return http.build();
    }

    @Bean   //정적 리소스에 대한 요청을 무시
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers(
                            PathRequest.toStaticResources().atCommonLocations()
                    );
        };
    }

}

