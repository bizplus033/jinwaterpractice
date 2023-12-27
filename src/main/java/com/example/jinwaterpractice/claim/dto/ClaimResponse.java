package com.example.jinwaterpractice.claim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClaimResponse {
    private Long id;
    private Long accountId;
    private String accountName;
    private LocalDate receiptDate;
    private String receiptContent;
    private String etc;
}
