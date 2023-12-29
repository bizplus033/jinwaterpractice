package com.example.jinwaterpractice.orderdetail;

import com.example.jinwaterpractice.order.JpaOrderRepository;
import com.example.jinwaterpractice.order.Order;
import com.example.jinwaterpractice.orderdetail.dto.CreateOrderDetailRequest;
import com.example.jinwaterpractice.orderdetail.dto.ListOrderDetailResponse;
import com.example.jinwaterpractice.orderdetail.dto.OrderDetailResponse;
import com.example.jinwaterpractice.orderdetail.dto.UpdateOrderDetailRequest;
import com.example.jinwaterpractice.product.JpaProductRepository;
import com.example.jinwaterpractice.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailService {
    private final JpaOrderDetailRepository jpaOrderDetailRepository;
    private final JpaProductRepository jpaProductRepository;
    private final JpaOrderRepository jpaOrderRepository;
    private final OrderDetailMapper orderDetailMapper;

    public OrderDetailResponse findOrderDetailById(Long orderDetailId) {
        log.info("OrderDetailService.findOrderDetailById");
        OrderDetail orderDetailPS = jpaOrderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RuntimeException("OrderDetail Not Found"));

        return orderDetailMapper.toOrderDetailResponse(orderDetailPS);
    }
    /**
     * Order.id로 OrderDetail 리스트 가져오기
     * */
    public List<ListOrderDetailResponse> findOrderDetailAllByOrderId(Long orderId) {
        log.info("OrderDetailService.findOrderDetailAllByOrderId");
        List<OrderDetail> listOrderDetail = jpaOrderDetailRepository.findOrderDetailByOrderId(orderId);
        return listOrderDetail.stream().map(orderDetailMapper::toListOrderDetailResponse).collect(Collectors.toList());
    }

    /**
     * Order.id랑 orderDetail.state로 OrderDetail 리스트 가져오기
     */
    public List<ListOrderDetailResponse> findOrderDetailListByIdAndState(Long orderId, Integer orderDetailState) {
        log.info("OrderDetailService.findOrderDetailListByIdAndState");
        List<OrderDetail> listOrderDetail = jpaOrderDetailRepository.findOrderDetailByOrderIdAndState(orderId, orderDetailState);
        return listOrderDetail.stream().map(orderDetailMapper::toListOrderDetailResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createOrderDetail(CreateOrderDetailRequest request) {
        log.info("OrderDetailService.createOrderDetail");
        Product productPS = jpaProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("product Not Found"));

        Order orderPS = jpaOrderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        jpaOrderDetailRepository.save(orderDetailMapper.toEntity(request, productPS, orderPS));

        // OrderDetail 생성 시 -> Order.amount 수량 증가
//        jpaOrderRepository.updateOrderAmountByOrderDetailAmount(request.getOrderId(), request.getOrderDetailAmount());
        orderPS.updateOrderAmount(request.getOrderDetailAmount());
    }

    @Transactional
    public void updateOrderDetail(UpdateOrderDetailRequest request) {
        OrderDetail orderDetailPS = jpaOrderDetailRepository.findById(request.getOrderDetailId())
                .orElseThrow(() -> new RuntimeException("OrderDetail Not Found"));
        Product productPS = jpaProductRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        Order orderPS = jpaOrderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        // OrderDetail.amount 수정시 -> Order.amount 수량 수정
        orderPS.updateOrderAmount(-orderDetailPS.getAmount()); // 기존 수량 (-)
        orderPS.updateOrderAmount(request.getOrderDetailAmount()); // 변경 수량 (+)
    }

    @Transactional
    public void updateDeleteStateOfOrderDetail(Long[] orderDetailIds) {
        for (Long id : orderDetailIds) {
            OrderDetail orderDetailPS = jpaOrderDetailRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("orderDetail Not Found"));
            jpaOrderDetailRepository.updateDeleteStateOfOrderDetail(id);

            // OrderDetail.amount 삭제 시 -> Order.amount 수량 감소
            Order order = orderDetailPS.getOrder();
            order.updateOrderAmount(-orderDetailPS.getAmount()); // 수량 감소
        }
    }
}
