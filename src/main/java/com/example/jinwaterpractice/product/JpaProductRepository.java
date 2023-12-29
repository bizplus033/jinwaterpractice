package com.example.jinwaterpractice.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product, Long>, DslProductRepository {

    Optional<Product> findByCode(String code);
}
