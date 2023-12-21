package com.example.jinwaterpractice.user.dto;

import com.example.jinwaterpractice.user.QUser;
import com.example.jinwaterpractice.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DslUserRepositoryImpl implements DslUserRepository{

    private final JPAQueryFactory query;
    private QUser user = QUser.user;
    @Override
    public boolean existsByUserId(String userId) {
        return false;
    }

    @Override
    public void updateDeleteStateOfUser(Long[] userIds) {

    }

    @Override
    public Page<User> findByUserAllBySearchKeyword(String userId, String name, Pageable pageable) {
        return null;
    }
}
