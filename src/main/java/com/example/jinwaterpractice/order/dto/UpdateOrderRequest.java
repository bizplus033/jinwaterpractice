package com.example.jinwaterpractice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UpdateOrderRequest {
    private Long orderId;

    private Long accountId;

    private String orderCode;

    private String manager;

    private Integer orderAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    private String etc;
}
