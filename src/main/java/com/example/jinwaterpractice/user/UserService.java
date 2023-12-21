package com.example.jinwaterpractice.user;

import com.example.jinwaterpractice.user.dto.ListUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    /**
     * UserDetailsService를 구현하면 무조건 구현해야 하는 메소드
     * */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> result = jpaUserRepository.findByUserId(userId);
        if (result.isPresent()) {
            return new UserAccount(result.get());
        }
        log.warn("{} 로 아이디 정보를 찾을 수 없습니다.", userId);
        throw new UsernameNotFoundException(userId);
    }

    /**
     * 회원가입
     * */
    @Transactional
    public void signup(String userId, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserId(userId);
        user.setUserId(encodedPassword);
        user.setDeleteState(0); // 0 이 false 즉, 삭제 되지 않은 상태
        jpaUserRepository.save(user);
    }

    /**
     * 특정 키워드로 모든 유저 찾기
     */
//    public Page<ListUserResponse> findUserAllBySearchKeyword(String userId, String name, Pageable pageable) {
//        jpaUserRepository.findB
//    }


}
