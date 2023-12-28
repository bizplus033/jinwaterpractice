package com.example.jinwaterpractice.orderdetail;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.example.jinwaterpractice.orderdetail.QOrderDetail.orderDetail;

@Repository
@RequiredArgsConstructor
public class DslOrderDetailRepositoryImpl implements DslOrderDetailRepository{
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Override
    public Page<OrderDetail> findOrderDetailAllBySearchKeyword(String orderCode, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<OrderDetail> content = query.selectFrom(orderDetail)
                .where(likeOrderCodeIgnoreCase(orderCode)
                        .and(goeStartDate(startDate))
                        .and(loeEndDate(endDate))
                )
                .orderBy(orderDetail.order.orderDate.desc())
                .orderBy(orderDetail.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = query.select(orderDetail.count())
                .where(likeOrderCodeIgnoreCase(orderCode)
                        .and(goeStartDate(startDate))
                        .and(loeEndDate(endDate))
                )
                .fetchOne();

        if (total == null) total = 0L;
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression loeEndDate(LocalDate endDate) {
        return endDate != null ? orderDetail.order.orderDate.loe(endDate) : null;
    }

    private  BooleanExpression goeStartDate(LocalDate startDate) {
        return startDate != null ? orderDetail.order.orderDate.goe(startDate) : null;
    }

    private BooleanExpression likeOrderCodeIgnoreCase(String orderCode) {
        return orderDetail.order.code.likeIgnoreCase("%" + ((orderCode == null) ? "" : orderCode) + "%");
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Long orderId) {
        return query.selectFrom(orderDetail)
                .where(orderDetail.deleteState.eq(0)
                        .and(orderDetail.order.id.eq(orderId)))
                .fetch();
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderIdAndState(Long orderId, Integer orderDetailState) {
        return query.selectFrom(orderDetail)
                .where(orderDetail.deleteState.eq(0)
                        .and(orderDetail.state.eq(orderDetailState))
                        .and(orderDetail.order.id.eq(orderId)))
                .fetch();
    }
    /**
     * update를 굳이 querydsl로 쓰는건 옳지 않다.
     * 변경 감지를 사용해서 값을 변경하자
     * */
    @Override
    public void updateDeleteStateOfOrderDetail(Long orderDetailId) {
        query.update(orderDetail)
                .set(orderDetail.deleteState, 1) // 0 -> 1
                .where(orderDetail.id.eq(orderDetailId))
                .execute();

        em.flush();
        em.clear();
    }
    /**
     * update를 굳이 querydsl로 쓰는건 옳지 않다.
     * 변경 감지를 사용해서 값을 변경하자
     * */
    @Override
    public void updateOrderDetailStateByIdAndState(Long orderDetailId, int state) {
        query.update(orderDetail)
                .set(orderDetail.state, state)
                .where(orderDetail.id.eq(orderDetailId))
                .execute();

        em.flush();
        em.clear();
    }
}
