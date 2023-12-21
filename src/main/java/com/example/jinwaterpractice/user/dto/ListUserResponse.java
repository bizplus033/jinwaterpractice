package com.example.jinwaterpractice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListUserResponse {
    private Long id;
    private String userId;
    private String name;
    private String department;
    private String position;
    private String tel;
    private String phone;
    private String email;
    private String etc;
}
