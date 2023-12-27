package com.example.jinwaterpractice.claim;

import com.example.jinwaterpractice.account.Account;
import com.example.jinwaterpractice.claim.dto.ClaimResponse;
import com.example.jinwaterpractice.claim.dto.CreateClaimRequest;
import com.example.jinwaterpractice.claim.dto.ListClaimResponse;
import com.example.jinwaterpractice.claim.dto.UpdateClaimRequest;
import org.springframework.stereotype.Component;

@Component
public class ClaimMapper {
    public ClaimResponse toClaimResponse(Claim claim) {
        ClaimResponse response = new ClaimResponse(
                claim.getId(),
                claim.getAccount().getId(),
                claim.getAccount().getName(),
                claim.getReceiptDate(),
                claim.getReceiptContent(),
                claim.getEtc()
        );
        return response;
    }

    public ListClaimResponse toListClaimResponse(Claim claim) {
        ListClaimResponse response = new ListClaimResponse(
                claim.getId(),
                claim.getAccount().getName(),
                claim.getReceiptDate(),
                claim.getReceiptContent(),
                claim.getEtc()
        );
        return response;
    }

    public Claim toEntity(CreateClaimRequest request, Account account) {
        Claim newClaim = new Claim();
        newClaim.setAccount(account);
        newClaim.setReceiptDate(request.getReceiptDate());
        newClaim.setReceiptContent(request.getReceiptContent());
        newClaim.setEtc(request.getEtc());
        return newClaim;
    }

    public Claim toEntity(UpdateClaimRequest request, Account account, Claim claim) {
        claim.setAccount(account);
        claim.setReceiptDate(request.getReceiptDate());
        claim.setReceiptContent(request.getReceiptContent());
        claim.setEtc(request.getEtc());
        return claim;
    }

}
