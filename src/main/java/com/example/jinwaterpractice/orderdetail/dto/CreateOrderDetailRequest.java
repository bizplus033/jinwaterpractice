package com.example.jinwaterpractice.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderDetailRequest {
    private Long productId;
    private Long orderId;
    private Integer orderDetailAmount;
    private String etc;
}
