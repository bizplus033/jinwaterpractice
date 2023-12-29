package com.example.jinwaterpractice.product.stock;

import com.example.jinwaterpractice.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslProductStockRepository {
    Page<ProductStock> findProductStockBySearchKeyword(String productCode, String productName, Pageable pageable);

    void updateProductStock(Long productStockId, Integer amount);
}
