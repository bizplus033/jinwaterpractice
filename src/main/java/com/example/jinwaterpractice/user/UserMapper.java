package com.example.jinwaterpractice.user;

import com.example.jinwaterpractice.user.dto.CreateUserRequest;
import com.example.jinwaterpractice.user.dto.UpdateUserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(User newUser, CreateUserRequest request) {
        newUser.setUserId(request.getUserId());
        newUser.setName(request.getName());
        newUser.setPassword(request.getPassword());
        newUser.setDepartment(request.getDepartment());
        newUser.setPosition(request.getPosition());
        newUser.setTel(request.getTel());
        newUser.setPhone(request.getPhone());
        newUser.setEmail(request.getEmail());
        newUser.setZipCode(request.getZipCode());
        newUser.setAddress(request.getAddress());
        newUser.setAddressDetail(request.getAddressDetail());
        newUser.setEtc(request.getEtc());
        return newUser;
    }

    public User toEntity(User user, UpdateUserRequest request) {
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setDepartment(request.getDepartment());
        user.setPosition(request.getPosition());
        user.setTel(request.getTel());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setZipCode(request.getZipCode());
        user.setAddress(request.getAddress());
        user.setAddressDetail(request.getAddressDetail());
        user.setEtc(request.getEtc());
        return user;
    }

}
