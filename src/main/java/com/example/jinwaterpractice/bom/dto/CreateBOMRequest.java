package com.example.jinwaterpractice.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBOMRequest {
    private Long productId;

    private Long materialId;

    private String materialCode;

    private String materialName;

    private Integer materialUnitPrice;

    private String etc;
}
