package com.example.jinwaterpractice.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductBOMResponse {
    private Long id;
    private Long materialId;
    private Long productId;

    private String materialCode;
    private String materialName;
    private Integer materialUnitPrice;
    private String etc;
}
