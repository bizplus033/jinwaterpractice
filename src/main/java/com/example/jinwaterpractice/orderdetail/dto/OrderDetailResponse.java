package com.example.jinwaterpractice.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private Long orderDetailId;
    private Long productId;
    private Long orderId;
    private String productCode;
    private String productName;
    private Integer productUnitPrice;
    private Integer orderDetailAmount;
    private String etc;
}
