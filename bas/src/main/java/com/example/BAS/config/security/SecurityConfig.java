package com.example.BAS.config.security;

import com.example.BAS.dao.user.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true) //시큐리티 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomFailureHandler customFailureHandler;
    private final UserDAO userDAO;


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandlerBean(userDAO);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable)

        .authorizeHttpRequests(request -> {
            request.requestMatchers(
                            new AntPathRequestMatcher("/main"),
                            new AntPathRequestMatcher("/signin/**"),
                            new AntPathRequestMatcher("/signup/**"),
                            new AntPathRequestMatcher("/img/**"),
                            new AntPathRequestMatcher("/js/**"),
                            new AntPathRequestMatcher("/assets/**"),
                            new AntPathRequestMatcher("/css/**"),
                            new AntPathRequestMatcher("/fonts/**"),
                            new AntPathRequestMatcher("/MDB5-STANDARD-UI-KIT-Free-6.4.1/**")
                    )
                    .permitAll()
                    .anyRequest().authenticated();
        })
        .formLogin(formLogin ->
                formLogin
                        .loginPage("/signin")
                        .permitAll()
                        .loginProcessingUrl("/signin")
                        .successHandler(customSuccessHandler())
                        .failureHandler(customFailureHandler)
        )
                .exceptionHandling((exception)->
                        exception.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler()));

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

