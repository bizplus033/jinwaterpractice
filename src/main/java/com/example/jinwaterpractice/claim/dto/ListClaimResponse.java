package com.example.jinwaterpractice.claim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ListClaimResponse {

    private Long id;
    private String accountName;
    private LocalDate receiptDate;
    private String receiptContent;
    private String etc;
}
