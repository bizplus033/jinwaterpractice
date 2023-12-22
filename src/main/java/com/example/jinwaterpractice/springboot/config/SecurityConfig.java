package com.example.jinwaterpractice.springboot.config;

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

import javax.naming.ldap.PagedResultsControl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

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
                // .failureUrl("/login")
                .permitAll()
                .and()
                .rememberMe()
                .key("my-remember-me-key")
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 7일
                .and()
                .logout().logoutSuccessUrl("/login")
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
