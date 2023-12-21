package com.example.jinwaterpractice.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;


@Getter
public class UserAccount extends User{

    private final com.example.jinwaterpractice.user.User user;

    public UserAccount(com.example.jinwaterpractice.user.User user){
        super(user.getUserId(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }
}
