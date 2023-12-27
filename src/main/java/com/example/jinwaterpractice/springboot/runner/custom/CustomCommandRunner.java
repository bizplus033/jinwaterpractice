package com.example.jinwaterpractice.springboot.runner.custom;

import com.example.jinwaterpractice.account.Account;
import com.example.jinwaterpractice.account.JpaAccountRepository;
import com.example.jinwaterpractice.account.dto.CreateAccountRequest;
import com.example.jinwaterpractice.claim.ClaimMapper;
import com.example.jinwaterpractice.claim.ClaimService;
import com.example.jinwaterpractice.claim.JpaClaimRepository;
import com.example.jinwaterpractice.claim.dto.CreateClaimRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CustomCommandRunner implements CommandLineRunner {
//    private final ClaimService claimService;
    private final JpaAccountRepository jpaAccountRepository;
    private final JpaClaimRepository jpaClaimRepository;
    private final ModelMapper modelMapper;
    private final ClaimMapper claimMapper;
    @Override
    public void run(String... args) throws Exception {
        CreateAccountRequest request
                = new CreateAccountRequest("A_00001", "매입처", "해린상사", "123-45-6789",
                "강해린", "유통", "물류", "02-345-9832", "073-2304-24", "haerin@naver.com",
                "21342", "제주시 영평동로 18", "43", "비에고");
        Account accountPS = jpaAccountRepository.save(modelMapper.map(request, Account.class));
        CreateClaimRequest claimRequest
                = new CreateClaimRequest(accountPS.getId(), "접수 컨텐츠", LocalDate.now(), "비에고2");
        jpaClaimRepository.save(claimMapper.toEntity(claimRequest, accountPS));


    }
}
