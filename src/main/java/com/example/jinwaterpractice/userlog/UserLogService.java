package com.example.jinwaterpractice.userlog;

import com.example.jinwaterpractice.user.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogService {

     private final JpaUserLogRepository jpaUserLogRepository;
     private final DslUserLogRepository dslUserLogRepository;
     private final JpaUserRepository jpaUserRepository;
     private final UserLogMapper userLogMapper;
}
