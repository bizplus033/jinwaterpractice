package com.example.jinwaterpractice.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DslOrderRepository {

    Page<Order> findOrderAllBySearchKeyword(String code, String accountName, Integer state, LocalDate startDate,
                                            LocalDate endDate, Pageable pageable);

    void updateDeleteStateOfOrder(Long[] ids);

    long updateOrderAmountByOrderDetailAmount(Long orderId, Integer orderDetailAmount);

    void updateOrderState(Long orderId, Integer state);

    List<Order> findOrderCodeMaxOneByCreatedAtToday();
}
