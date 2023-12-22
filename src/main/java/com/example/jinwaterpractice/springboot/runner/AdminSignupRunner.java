package com.example.jinwaterpractice.springboot.runner;

import com.example.jinwaterpractice.user.JpaUserRepository;
import com.example.jinwaterpractice.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminSignupRunner implements CommandLineRunner {
    private final UserService userService;

    private final JpaUserRepository jpaUserRepository;

    private final String adminId;

    private final String adminPassword;
    public AdminSignupRunner(UserService userService,
                             JpaUserRepository jpaUserRepository,
                             @Value("admin") String adminId,
                             @Value("1234") String adminPassword) {
        this.userService = userService;
        this.jpaUserRepository = jpaUserRepository;
        this.adminId = adminId;
        this.adminPassword = adminPassword;
    }
    @Override
    public void run(String... args) throws Exception {
        if (!jpaUserRepository.existsByUserId(adminId)) {
            userService.signup(adminId, adminPassword);
        }

    }
}
