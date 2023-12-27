package com.example.jinwaterpractice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String code;
    private String type;
    private String name;
    private String businessNumber;
    private String ceo;
    private String businessType;
    private String businessCategory;
    private String tel;
    private String fax;
    private String email;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String etc;
}
