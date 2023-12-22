package com.example.jinwaterpractice.main;

import com.example.jinwaterpractice.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(HttpServletRequest request) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if (user != null && !user.equals("anonymousUser")) {
            return "redirect:/list";
        }
        return "login";
    }
}
