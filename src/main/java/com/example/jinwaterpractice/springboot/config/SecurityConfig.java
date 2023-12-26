package com.example.jinwaterpractice.springboot.config;

import com.example.jinwaterpractice.springboot.config.custom.CustomAuthenticationSuccessHandler;
import com.example.jinwaterpractice.springboot.config.custom.CustomLogoutSuccessHandler;
import com.example.jinwaterpractice.userlog.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserLogService userLogService;
    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler(userLogService);
    }

    @Bean
    public LogoutHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler(userLogService);
    }
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // h2-console
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests()
                .mvcMatchers("/login", "/fonts/**", "/css/**", "/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/list", true)
                .successHandler(customAuthenticationSuccessHandler()) // 로그인 성공 후

                // .failureUrl("/login")
                .permitAll()
                .and()
                .rememberMe()
                .key("my-remember-me-key")
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 7일
                .and()
                .logout()
                .addLogoutHandler(customLogoutSuccessHandler())  // 로그아웃 성공 후
                .logoutSuccessUrl("/login")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/login")

                .and()
                .csrf().disable()
                .headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
