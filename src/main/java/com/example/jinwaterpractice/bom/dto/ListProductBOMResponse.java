package com.example.jinwaterpractice.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListProductBOMResponse {
    private Long id;

    private String materialCode;

    private String materialName;

    private Integer materialUnitPrice;

    private String etc;
}
