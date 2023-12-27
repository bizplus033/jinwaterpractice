package com.example.jinwaterpractice.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslAccountRepository {

    Page<Account> findAccountAllBySearchKeyword(String type, String code, String name, Pageable pageable);

    void updateDeleteStateOfAccount(Long[] accountIds);

    String findAccountCodeMaxOne();
}
