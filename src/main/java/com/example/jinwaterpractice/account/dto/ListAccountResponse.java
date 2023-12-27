package com.example.jinwaterpractice.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListAccountResponse {
    private Long id;
    private String type;
    private String code;
    private String name;
    private String businessNumber;
    private String ceo;
    private String etc;
}
