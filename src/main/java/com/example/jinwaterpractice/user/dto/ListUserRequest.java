package com.example.jinwaterpractice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.GeneratedValue;

@Getter
@AllArgsConstructor
public class ListUserRequest {
    private String userId;
    private String name;
}
