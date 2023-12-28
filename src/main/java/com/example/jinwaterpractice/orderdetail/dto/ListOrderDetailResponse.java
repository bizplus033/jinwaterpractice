package com.example.jinwaterpractice.orderdetail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListOrderDetailResponse {
    private Long id;

    private String OrderCode;

    private String productCode;

    private String productName;

    private Integer productUnitPrice;

    private Integer amount;

    private Integer state;

    private String etc;
}
