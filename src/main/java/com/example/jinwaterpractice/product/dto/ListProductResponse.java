package com.example.jinwaterpractice.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProductResponse {
    private Long id;
    private String code;
    private String name;
    private Integer unitPrice;
    private String etc;
}
