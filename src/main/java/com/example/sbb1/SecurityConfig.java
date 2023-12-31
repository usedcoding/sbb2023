package com.example.sbb1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//위 두 개의 annotation은 반드시 있어야 security 사용 가능
@EnableMethodSecurity(prePostEnabled = true)
//위 annotation이 있어야 @PreAuthorize("isAuthenticated()") annotation이 작동 한다.
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        //localhost8080이후에 모든 요청(/**)에 대한 권한을 연다.
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .formLogin((formLogin) -> formLogin
                        //GET
                        // 시큐리티 사용자가 만든 로그인 페이지 url을 알려준다.
                        // 없으면 기본 url은 /login
                        .loginPage("/user/login")
                        //POST
                        //시큐리티에게 로그인 폼 요청url을 알려준다.
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
        ;
        return http.build();
    }
        @Bean
        PasswordEncoder passwordEncoder() {
        //복호화가 안되는 암호화 알고리즘(password 저장시 필수 알고리즘)
            return new BCryptPasswordEncoder();
        }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
