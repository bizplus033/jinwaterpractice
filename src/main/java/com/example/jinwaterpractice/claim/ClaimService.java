package com.example.jinwaterpractice.claim;

import com.example.jinwaterpractice.account.Account;
import com.example.jinwaterpractice.account.JpaAccountRepository;
import com.example.jinwaterpractice.claim.dto.ClaimResponse;
import com.example.jinwaterpractice.claim.dto.CreateClaimRequest;
import com.example.jinwaterpractice.claim.dto.ListClaimResponse;
import com.example.jinwaterpractice.claim.dto.UpdateClaimRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClaimService {
    private final JpaClaimRepository jpaClaimRepository;
    private final JpaAccountRepository jpaAccountRepository;
    private final ClaimMapper claimMapper;

    public ClaimResponse findClaimById(Long id) {
        Claim claimPS = jpaClaimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim Not Found"));
        return claimMapper.toClaimResponse(claimPS);
    }

    public Page<ListClaimResponse> findClaimAllByKeyword(String accountName, LocalDate startDate, LocalDate endDate,
                                                         Pageable pageable) {
        Page<Claim> claimPage = jpaClaimRepository.findClaimByKeyword(accountName, startDate, endDate, pageable);

        // return claimPage.map(claim -> claimMapper.toListClaimResponse(claim));
        return claimPage.map(claimMapper::toListClaimResponse);
    }
    @Transactional
    public void createClaim(CreateClaimRequest request) {
        Account accountPS = jpaAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        jpaClaimRepository.save(claimMapper.toEntity(request, accountPS));
    }
    @Transactional
    public void updateClaim(UpdateClaimRequest request) {
        Account accountPS = jpaAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        Claim claimPS = jpaClaimRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Claim Not Found"));
        claimMapper.toEntity(request, accountPS, claimPS);
    }
}
