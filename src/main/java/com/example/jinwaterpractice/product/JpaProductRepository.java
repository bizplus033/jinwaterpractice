package com.example.jinwaterpractice.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
