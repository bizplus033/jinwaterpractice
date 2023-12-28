package com.example.jinwaterpractice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrderRequest {
    private Long accountId;

    private String orderCode;

    private String manager;

    private Integer orderAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private String etc;
}
