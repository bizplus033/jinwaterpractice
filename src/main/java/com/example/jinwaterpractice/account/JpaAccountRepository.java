package com.example.jinwaterpractice.account;

import com.example.jinwaterpractice.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long>, DslAccountRepository {
}
