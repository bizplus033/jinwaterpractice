package com.example.jinwaterpractice.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateOrderDetailRequest {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;

    private Integer orderDetailAmount;

    private String etc;
}
