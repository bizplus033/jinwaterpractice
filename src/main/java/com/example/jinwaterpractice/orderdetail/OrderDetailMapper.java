package com.example.jinwaterpractice.orderdetail;

import com.example.jinwaterpractice.order.Order;
import com.example.jinwaterpractice.orderdetail.dto.CreateOrderDetailRequest;
import com.example.jinwaterpractice.orderdetail.dto.ListOrderDetailResponse;
import com.example.jinwaterpractice.orderdetail.dto.OrderDetailResponse;
import com.example.jinwaterpractice.orderdetail.dto.UpdateOrderDetailRequest;
import com.example.jinwaterpractice.product.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        OrderDetailResponse response = new OrderDetailResponse(
                orderDetail.getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getCode(),
                orderDetail.getProduct().getName(),
                orderDetail.getProduct().getUnitPrice(),
                orderDetail.getAmount(),
                orderDetail.getEtc()
        );
        return response;
    }

    public ListOrderDetailResponse toListOrderDetailResponse(OrderDetail orderDetail) {
        ListOrderDetailResponse response = new ListOrderDetailResponse(
                orderDetail.getId(),
                orderDetail.getOrder().getCode(),
                orderDetail.getProduct().getCode(),
                orderDetail.getProduct().getName(),
                orderDetail.getProduct().getUnitPrice(),
                orderDetail.getAmount(),
                orderDetail.getState(),
                orderDetail.getEtc()
        );
        return response;
    }

    public OrderDetail toEntity(CreateOrderDetailRequest request, Product product, Order order) {
        OrderDetail newOrderDetail = new OrderDetail();
        newOrderDetail.setProduct(product);
        newOrderDetail.setOrder(order);
        newOrderDetail.setAmount(request.getOrderDetailAmount());
        newOrderDetail.setEtc(request.getEtc());
        return newOrderDetail;
    }

    public OrderDetail toEntity(UpdateOrderDetailRequest request, Product product, OrderDetail orderDetail) {
        orderDetail.setProduct(product);
        orderDetail.setAmount(request.getOrderDetailAmount());
        orderDetail.setEtc(request.getEtc());
        return orderDetail;
    }
}
