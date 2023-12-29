package com.example.jinwaterpractice.bom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProductBOMRepository extends JpaRepository<ProductBOM, Long>, DslProductBOMRepository {

}
