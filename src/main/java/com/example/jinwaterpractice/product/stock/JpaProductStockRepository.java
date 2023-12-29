package com.example.jinwaterpractice.product.stock;

import com.example.jinwaterpractice.product.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductStockRepository extends JpaRepository<ProductStock, Long>, DslProductStockRepository {
}
