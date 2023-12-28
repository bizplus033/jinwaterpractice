package com.example.jinwaterpractice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ListOrderResponse {
    private Long orderId; // 수주 아이디

    private String orderCode; // 수주 코드

    private String accountName; // 거래처 이름

    private String manager; // 담당자

    private LocalDate orderDate; // 수주일

    private LocalDate releaseDate; // 출고일

    private Integer orderAmount; // 총 수주량

    private Integer orderState; // 수주 상태 코드

    private String etc; // 수주에 대한 비고
}
