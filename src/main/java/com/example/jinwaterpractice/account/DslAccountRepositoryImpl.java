package com.example.jinwaterpractice.account;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jinwaterpractice.account.QAccount.account;

@Repository
@RequiredArgsConstructor
public class DslAccountRepositoryImpl implements DslAccountRepository{

    private final JPAQueryFactory query;

    @Override
    public Page<Account> findAccountAllBySearchKeyword(String type, String code, String name, Pageable pageable) {
        List<Account> content = query.selectFrom(account)
                .where(account.deleteState.eq(0)
                        .and(likeTypeIgnoreCase(type))
                        .and(likeCodeIgnoreCase(code))
                        .and(likeNameIgnoreCase(name)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = query.select(account.count())
                .from(account)
                .where(account.deleteState.eq(0)
                        .and(likeTypeIgnoreCase(type))
                        .and(likeCodeIgnoreCase(code))
                        .and(likeNameIgnoreCase(name)))
                .fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void updateDeleteStateOfAccount(Long[] accountIds) {
        query.update(account)
                .set(account.deleteState, 1) // 1이 true, 삭제가 true라는 것!
                .where(account.id.in(accountIds))
                .execute();
    }

    @Override
    public String findAccountCodeMaxOne() {
        return query.select(account.code)
                .from(account)
                .orderBy(account.createdAt.desc())
                .offset(0)
                .limit(1)
                .fetchOne();
    }

    private BooleanExpression likeTypeIgnoreCase(String type){
        return account.type.likeIgnoreCase("%" + ((type == null) ? "" : type) + "%");
    }

    private BooleanExpression likeCodeIgnoreCase(String code){
        return account.code.likeIgnoreCase("%" + ((code == null) ? "" : code) + "%");
    }

    private BooleanExpression likeNameIgnoreCase(String name){
        return account.name.likeIgnoreCase("%" + ((name == null) ? "" : name) + "%");
    }
}
