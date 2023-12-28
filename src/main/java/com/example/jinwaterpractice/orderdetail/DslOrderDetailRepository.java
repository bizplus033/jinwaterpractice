package com.example.jinwaterpractice.orderdetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DslOrderDetailRepository {

    Page<OrderDetail> findOrderDetailAllBySearchKeyword(String orderCode, LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<OrderDetail> findOrderDetailByOrderId(Long orderId);

    List<OrderDetail> findOrderDetailByOrderIdAndState(Long orderId, Integer orderDetailState);

    void updateDeleteStateOfOrderDetail(Long orderDetailId);

    void updateOrderDetailStateByIdAndState(Long orderDetailId, int state);
}
