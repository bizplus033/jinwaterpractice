package com.example.jinwaterpractice.claim;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClaimRepository extends JpaRepository<Claim, Long>, DslClaimRepository {
}
