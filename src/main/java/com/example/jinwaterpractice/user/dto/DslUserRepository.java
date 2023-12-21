package com.example.jinwaterpractice.user.dto;

import com.example.jinwaterpractice.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslUserRepository {

    boolean existsByUserId(String userId);

    void updateDeleteStateOfUser(Long[] userIds);

    Page<User> findByUserAllBySearchKeyword(String userId, String name, Pageable pageable);
}
