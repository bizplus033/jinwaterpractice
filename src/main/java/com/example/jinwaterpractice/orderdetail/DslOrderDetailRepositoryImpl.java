package com.example.jinwaterpractice.orderdetail;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return null;
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(Long orderId) {
        return null;
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
