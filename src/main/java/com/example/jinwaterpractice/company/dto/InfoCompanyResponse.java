package com.example.jinwaterpractice.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoCompanyResponse {
    private Long id;
    private String name;
    private String ceo;
    private String businessNumber;
    private String businessType;
    private String businessCategory;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String tel;
    private String fax;
    private String email;
}
