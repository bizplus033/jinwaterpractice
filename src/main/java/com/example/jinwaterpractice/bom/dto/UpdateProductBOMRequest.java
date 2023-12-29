package com.example.jinwaterpractice.bom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProductBOMRequest {
    private Long id;
    private Long materialId;
    private String etc;
}
