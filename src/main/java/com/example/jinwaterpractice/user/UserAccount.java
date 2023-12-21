package com.example.jinwaterpractice.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

// 스프링 시큐리티가 제공하는 User 객체는 UserDetails 구현체
// UserDetails를 구현한 객체를 서비스단에서 리턴해줘야 한다.

@Getter
public class UserAccount extends User{

    private final com.example.jinwaterpractice.user.User user;

    public UserAccount(com.example.jinwaterpractice.user.User user){
        super(user.getUserId(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }
}
