package com.example.jinwaterpractice.order;

import com.example.jinwaterpractice.account.Account;
import com.example.jinwaterpractice.order.dto.CreateOrderRequest;
import com.example.jinwaterpractice.order.dto.ListOrderResponse;
import com.example.jinwaterpractice.order.dto.OrderResponse;
import com.example.jinwaterpractice.order.dto.UpdateOrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public ListOrderResponse toListOrderResponse(Order order) {
        return new ListOrderResponse(
                order.getId(),
                order.getCode(),
                order.getAccount().getName(),
                order.getManager(),
                order.getOrderDate(),
                order.getReleaseDate(),
                order.getOrderAmount(),
                order.getState(),
                order.getEtc()
        );
    }
    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getAccount().getId(),
                order.getCode(),
                order.getManager(),
                order.getAccount().getCode(),
                order.getAccount().getName(),
                order.getOrderDate(),
                order.getReleaseDate(),
                order.getState(),
                order.getOrderAmount(),
                order.getEtc()
        );
    }
    public Order toEntity(CreateOrderRequest request, Account account) {
        Order newOrder = new Order();
        newOrder.setAccount(account);
        newOrder.setCode(request.getOrderCode());
        newOrder.setManager(request.getManager());
        newOrder.setOrderAmount(request.getOrderAmount());
        newOrder.setOrderDate(request.getOrderDate());
        newOrder.setReleaseDate(request.getReleaseDate());
        newOrder.setEtc(request.getEtc());
        newOrder.setOrderAmount(0);
        return newOrder;
    }

    public Order toEntity(UpdateOrderRequest request, Account account, Order order) {
        order.setAccount(account);
        order.setCode(request.getOrderCode());
        order.setManager(request.getManager());
        order.setOrderAmount(request.getOrderAmount());
        order.setOrderDate(request.getOrderDate());
        order.setReleaseDate(request.getReleaseDate());
        order.setEtc(request.getEtc());
        return order;
    }
}
