package com.example.jinwaterpractice.product;

import com.example.jinwaterpractice.product.dto.CreateProductRequest;
import com.example.jinwaterpractice.product.dto.ListProductResponse;
import com.example.jinwaterpractice.product.dto.ProductResponse;
import com.example.jinwaterpractice.product.dto.UpdateProductRequest;
import com.example.jinwaterpractice.product.stock.JpaProductStockRepository;
import com.example.jinwaterpractice.product.stock.ProductStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.LongPredicate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final JpaProductRepository jpaProductRepository;
    private final JpaProductStockRepository jpaProductStockRepository;
    private final ModelMapper modelMapper;

    public Page<ListProductResponse> findProductAllBySearchKeyword(String code, String name, Pageable pageable) {
        log.info("ProductService.findProductAllBySearchKeyword");
        Page<Product> productPage = jpaProductRepository.findProductAllBySearchKeyword(code, name, pageable);
        return productPage.map(product -> modelMapper.map(product, ListProductResponse.class));
    }

    public ProductResponse findProductById(Long id) {
        log.info("ProductService.findProductById");
        return jpaProductRepository.findById(id).map(product -> modelMapper.map(product, ProductResponse.class))
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        log.info("ProductService.createProduct");
        Product productPS = jpaProductRepository.save(modelMapper.map(request, Product.class));

        // 제품 생성시 제품에 대한 제품 재고 생성
        ProductStock productStock = ProductStock.factory(productPS);
        jpaProductStockRepository.save(productStock);
        return productPS;
    }

    @Transactional
    public void updateProduct(UpdateProductRequest request) {
        log.info("ProductService.updateProduct");
        Product productPS = jpaProductRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        modelMapper.map(request, productPS);
    }

    @Transactional
    public void updateProductDeleteState(Long[] productIds) {
        log.info("ProductService.updateProductDeleteState");
        jpaProductRepository.updateDeleteStateOfProduct(productIds);
    }

}
