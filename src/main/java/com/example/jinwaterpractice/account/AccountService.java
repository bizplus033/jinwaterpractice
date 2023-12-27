package com.example.jinwaterpractice.account;

import com.example.jinwaterpractice.account.dto.AccountResponse;
import com.example.jinwaterpractice.account.dto.CreateAccountRequest;
import com.example.jinwaterpractice.account.dto.ListAccountResponse;
import com.example.jinwaterpractice.account.dto.UpdateAccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final JpaAccountRepository jpaAccountRepository;
    private final ModelMapper modelMapper;

    /**
     * 모두 가져와서 뿌려주기
     * 검색 조건 추가 가능
     * */
    public Page<ListAccountResponse> findAccountAllBySearchKeyword(String type, String code, String name, Pageable pageable) {
        Page<Account> accountPage = jpaAccountRepository.findAccountAllBySearchKeyword(type, code, name, pageable);
        return accountPage.map(account -> modelMapper.map(account, ListAccountResponse.class));
    }

    /**
     * 생성
     * */
    public void createAccount(CreateAccountRequest request){
        jpaAccountRepository.save(modelMapper.map(request, Account.class));
    }

    /**
     * account id로 찾기
     * */
    public AccountResponse findAccountById(Long accountId) {
        return jpaAccountRepository.findById(accountId).map(account -> modelMapper.map(account, AccountResponse.class))
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
//        Account accountPS = jpaAccountRepository.findById(accountId)
//                .orElseThrow(() -> new RuntimeException("Account Not Found"));
//        AccountResponse accountResponse = modelMapper.map(accountPS, AccountResponse.class);
//        return accountResponse;
    }

    /**
     * 업데이트 메서드
     * */
    @Transactional
    public void updateAccount(UpdateAccountRequest request) {
        Account accountPS = jpaAccountRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Access Not Found"));

        modelMapper.map(request, accountPS);
    }

    /**
     * 삭제 메서드
     * */
    @Transactional
    public void updateDeleteStateOfAccount(Long[] accountIds) {
        jpaAccountRepository.updateDeleteStateOfAccount(accountIds);
    }

    // A_00001
    public String createAccountCode() {
        String newAccountCode = "A_";
        String accountCodeMaxOne = jpaAccountRepository.findAccountCodeMaxOne();
        if (accountCodeMaxOne == null) {
            return newAccountCode + "00001";
        } else {
            int newNum = Integer.parseInt(accountCodeMaxOne.substring(2)) + 1;
            String newNumToString = String.valueOf(newNum);

            while(newNumToString.length() < 5) {
                newNumToString = "0" + newNumToString;
            }
            return newAccountCode + newNumToString;
        }
    }
}
