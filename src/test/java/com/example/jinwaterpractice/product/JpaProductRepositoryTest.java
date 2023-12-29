package com.example.jinwaterpractice.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class JpaProductRepositoryTest {
    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    public void test_findByCode() {
        // given
        String code = "a_123";
        String name = "물티슈";
        int unitPrice = 3500;
        String etc = "비고비고비고";

        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setUnitPrice(unitPrice);
        product.setEtc(etc);

        // when
        jpaProductRepository.save(product);
        Optional<Product> byCode = jpaProductRepository.findByCode(code);
        Product product1 = byCode.get();

        // then
        Assertions.assertThat(product1.getName()).isEqualTo("물티슈");
        Assertions.assertThat(product1.getUnitPrice()).isEqualTo(3500);
    }

    @Test
    public void test_findByCode_null() {
        // given
        String code = "a_123";
        String anotherCode = "b_123";
        String name = "물티슈";
        int unitPrice = 3500;
        String etc = "비고비고비고";

        Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setUnitPrice(unitPrice);
        product.setEtc(etc);

        // when
        jpaProductRepository.save(product);
        Optional<Product> byCode = jpaProductRepository.findByCode(anotherCode);


        // then
        Assertions.assertThat(byCode.isPresent()).isFalse();
    }
}
