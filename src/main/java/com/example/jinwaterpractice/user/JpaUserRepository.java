package com.example.jinwaterpractice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long>, DslUserRepository {
    Optional<User> findByUserId(String userId);
}
