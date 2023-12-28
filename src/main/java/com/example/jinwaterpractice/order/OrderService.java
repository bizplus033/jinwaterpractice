package com.example.jinwaterpractice.order;

import com.example.jinwaterpractice.account.Account;
import com.example.jinwaterpractice.account.JpaAccountRepository;
import com.example.jinwaterpractice.order.dto.CreateOrderRequest;
import com.example.jinwaterpractice.order.dto.ListOrderResponse;
import com.example.jinwaterpractice.order.dto.OrderResponse;
import com.example.jinwaterpractice.order.dto.UpdateOrderRequest;
import com.example.jinwaterpractice.orderdetail.JpaOrderDetailRepository;
import com.example.jinwaterpractice.orderdetail.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final JpaOrderRepository jpaOrderRepository;
    private final JpaAccountRepository jpaAccountRepository;
    private final JpaOrderDetailRepository jpaOrderDetailRepository;
    private final OrderMapper orderMapper;

    public Page<ListOrderResponse> findOrderAllBySearchKeyword(String code, String accountName, Integer state, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("OrderService.findOrderAllBySearchKeyword");
        Page<Order> orderPage = jpaOrderRepository.findOrderAllBySearchKeyword(code, accountName, state, startDate, endDate, pageable);
        return orderPage.map(orderMapper::toListOrderResponse);
    }

    public OrderResponse findOrderById(Long orderId) {
        log.info("OrderService.findOrderById");
        return jpaOrderRepository.findById(orderId)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        log.info("OrderService.createOrder");
        Account accountPS = jpaAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
        return jpaOrderRepository.save(orderMapper.toEntity(request, accountPS));

    }

    @Transactional
    public void updateOrder(UpdateOrderRequest request) {
        log.info("OrderService.updateOrder");
        Order orderPS = jpaOrderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        Account accountPS = jpaAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account Not Found"));
        orderMapper.toEntityForUpdate(request, accountPS, orderPS);
    }
    /**
     * orderDetail이 모두 출하검사 완료라면
     * order의 상태가 1(출하완료)로 변경
     * */
    @Transactional
    public void updateOrderState(Long orderId) {
        log.info("OrderService.updateOrderState");
        int orderDetailShippingStatus = 3; // 수주제품: 출하검사 완료
        int orderShippingStatus = 1; // 수주: 출하완료
        boolean result = false;

        List<OrderDetail> listOrderDetail = jpaOrderDetailRepository.findOrderDetailByOrderId(orderId);

        // 한 주문에 orderDetail들이 모두 출하검사 완료라면
        if (!listOrderDetail.isEmpty()) {
            result = listOrderDetail.stream().allMatch(od -> od.getState() == orderDetailShippingStatus);
        }

        if (result) {
            Order orderPs = jpaOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
            orderPs.updateOrderState(orderShippingStatus);
        }
    }
    @Transactional
    public void updateDeleteStateOfOrder(Long[] orderIds) {
        log.info("OrderService.updateDeleteStateOfOrder");
        jpaOrderRepository.updateDeleteStateOfOrder(orderIds);
    }

    public String findOrderCodeOneByCreatedAtToday() {
        log.info("OrderService.findOrderCodeOneByCreatedAtToday");
        List<Order> listOrder = jpaOrderRepository.findOrderCodeMaxOneByCreatedAtToday();
        if (!listOrder.isEmpty()) {
            return listOrder.get(0).getCode();
        }
        return null;
    }

}
