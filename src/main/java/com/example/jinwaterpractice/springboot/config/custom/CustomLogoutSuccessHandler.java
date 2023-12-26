package com.example.jinwaterpractice.springboot.config.custom;

import com.example.jinwaterpractice.user.UserAccount;
import com.example.jinwaterpractice.userlog.UserLogService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 로그아웃 성공시 처리할 작업 추가
 * UserLog를 생성하고 저장
 * */
@Slf4j
//@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutHandler {
    private final UserLogService userLogService;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // 로그아웃 성공시 처리할 작업 추가
        log.info("logout success : 로그아웃 성공");
        UserAccount principal = (UserAccount) authentication.getPrincipal();
        userLogService.createUserLogoutV2(request, principal.getUsername());

        // 리다이렉트 또는 응답 처리
        response.sendRedirect("/");
    }
}
