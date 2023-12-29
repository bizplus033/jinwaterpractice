package com.example.jinwaterpractice.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslProductRepository {
    Page<Product> findProductAllBySearchKeyword(String code, String name, Pageable pageable);

    void updateDeleteStateOfProduct(Long[] productIds);
}
