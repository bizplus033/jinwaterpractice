package com.example.jinwaterpractice.order;

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
import static com.example.jinwaterpractice.order.QOrder.order;
@Repository
@RequiredArgsConstructor
public class DslOrderRepositoryImpl implements DslOrderRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Order> findOrderAllBySearchKeyword(String code, String accountName, Integer state, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<Order> content = queryFactory.selectFrom(order)
                .leftJoin(account)
                .on(order.account.id.eq(account.id))
                .where(order.deleteState.eq(0)
                        .and(likeCodeIgnoreCase(code))
                        .and(likeAccountNameIgnoreCase(accountName))
                        .and(likeStateIgnoreCase(state))
                        .and(goeStartDate(startDate))
                        .and(loeEndDate(endDate))
                ).orderBy(order.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = queryFactory.select(order.count())
                .from(order)
                .leftJoin(account)
                .on(order.account.id.eq(account.id))
                .where(order.deleteState.eq(0)
                        .and(likeCodeIgnoreCase(code))
                        .and(likeAccountNameIgnoreCase(accountName))
                        .and(likeStateIgnoreCase(state))
                        .and(goeStartDate(startDate))
                        .and(loeEndDate(endDate))
                )
                .fetchOne();

        if (total == null) total = 0L;
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void updateDeleteStateOfOrder(Long[] ids) {
        queryFactory.update(order)
                .set(order.deleteState, 1)
                .where(order.id.in(ids))
                .execute();
    }

    /**
     * 몇 개 레코드가 반영되었는지 반환
     * 여기서는 orderId가 하나니까 1이 반환되겠지
     * */
    @Override
    public long updateOrderAmountByOrderDetailAmount(Long orderId, Integer orderDetailAmount) {
        return queryFactory.update(order)
                .set(order.orderAmount, order.orderAmount.add(orderDetailAmount))
                .where(order.id.eq(orderId))
                .execute();

    }

    @Override
    public void updateOrderState(Long orderId, Integer state) {
        queryFactory.update(order)
                .set(order.state, state)
                .where(order.id.eq(orderId))
                .execute();
    }

    @Override
    public List<Order> findOrderCodeMaxOneByCreatedAtToday() {
        return queryFactory.selectFrom(order)
                .where(order.createdAt.eq(LocalDate.now()))
                .orderBy(order.createdAt.desc())
                .limit(1)
                .fetch();
    }
    private BooleanExpression loeEndDate(LocalDate endDate) {
        return endDate != null ? order.orderDate.loe(endDate) : null;
    }

    private BooleanExpression goeStartDate(LocalDate startDate) {
        return startDate != null ? order.orderDate.goe(startDate) : null;
    }

    private BooleanExpression likeStateIgnoreCase(Integer state) {
        return order.state.like("%" + ((state == null) ? "" : state) + "%");
    }

    private BooleanExpression likeAccountNameIgnoreCase(String accountName) {
        return order.account.name.likeIgnoreCase("%" + ((accountName == null) ? "" : accountName) + "%");
    }

    private BooleanExpression likeCodeIgnoreCase(String code) {
        return order.code.likeIgnoreCase("%" + ((code == null) ? "" : code) + "%");
    }


}
