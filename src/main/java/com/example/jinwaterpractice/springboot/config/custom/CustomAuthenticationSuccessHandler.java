package com.example.jinwaterpractice.springboot.config.custom;

import com.example.jinwaterpractice.user.UserAccount;
import com.example.jinwaterpractice.userlog.UserLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 로그인 성공 시 처리할 작업 추가
 * UserLog를 생성하고 저장
 * */
@Slf4j
//@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserLogService userLogService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 성공시 처리할 작업 추가
        // UserLog를 생성하고 저장하는 코드
        log.info("Authentication success : 로그인 성공");
        UserAccount principal = (UserAccount) authentication.getPrincipal();
        userLogService.createUserLoginV2(request, principal.getUser());

        // 리다이렉트 또는 응답 처리
        response.sendRedirect("/list");
    }
}
