package com.example.jinwaterpractice.claim;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface DslClaimRepository {

    Page<Claim> findClaimByKeyword(String accountName, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
