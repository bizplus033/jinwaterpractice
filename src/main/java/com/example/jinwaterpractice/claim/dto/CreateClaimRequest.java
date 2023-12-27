package com.example.jinwaterpractice.claim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateClaimRequest {
    private Long accountId;
    private String receiptContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiptDate;

    private String etc;
}
