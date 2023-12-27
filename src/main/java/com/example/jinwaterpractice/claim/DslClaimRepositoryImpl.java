package com.example.jinwaterpractice.claim;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.example.jinwaterpractice.account.QAccount.account;
import static com.example.jinwaterpractice.claim.QClaim.claim;

@Repository
@RequiredArgsConstructor
public class DslClaimRepositoryImpl implements DslClaimRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Claim> findClaimByKeyword(String accountName, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<Claim> content = queryFactory.selectFrom(claim)
                .join(claim.account, account)
                .where(likeAccountNameIgnoreCase(accountName)
                        .and(startDateClause(startDate))
                        .and(endDateClause(endDate))
                )
                .orderBy(claim.receiptDate.desc())
                .orderBy(claim.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = queryFactory.select(claim.count())
                .from(claim)
                .join(claim.account, account)
                .where(likeAccountNameIgnoreCase(accountName)
                        .and(startDateClause(startDate))
                        .and(endDateClause(endDate))
                ).fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression likeAccountNameIgnoreCase(String accountName) {
        return account.name.likeIgnoreCase("%" + ((accountName == null) ? "" : accountName) + "%");
    }

    private BooleanExpression startDateClause(LocalDate startDate) {
        return startDate != null ? claim.receiptDate.goe(startDate) : null;
    }

    private BooleanExpression endDateClause(LocalDate endDate) {
        return endDate != null ? claim.receiptDate.loe(endDate) : null;
    }
}
