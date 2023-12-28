package com.example.jinwaterpractice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;

    private Long AccountId;

    private String orderCode;

    private String manager;

    private String accountCode;

    private String accountName;

    private LocalDate orderDate;

    private LocalDate releaseDate;

    private Integer orderState;

    private Integer orderAmount;

    private String etc;
}
