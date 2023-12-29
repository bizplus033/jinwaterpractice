package com.example.jinwaterpractice.bom;

import java.util.List;

public interface DslProductBOMRepository {
    List<ProductBOM> findProductBOMByProductId(Long productId);

    void updateDeleteStateOfProductBOM(Long[] ids);
}
