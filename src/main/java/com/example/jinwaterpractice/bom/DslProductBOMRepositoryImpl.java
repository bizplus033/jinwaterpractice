package com.example.jinwaterpractice.bom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jinwaterpractice.bom.QProductBOM.productBOM;
import static com.example.jinwaterpractice.product.QProduct.product;

@Repository
@RequiredArgsConstructor
public class DslProductBOMRepositoryImpl implements DslProductBOMRepository{
    private final JPAQueryFactory query;

    // 얘는 페이징을 안하나봄
    @Override
    public List<ProductBOM> findProductBOMByProductId(Long productId) {
        return query.selectFrom(productBOM)
                .innerJoin(productBOM.product, product)
                .where(productBOM.deleteState.eq(0)
                        .and(productBOM.product.id.eq(productId)))
                .fetch();
    }

    @Override
    public void updateDeleteStateOfProductBOM(Long[] ids) {
        query.update(productBOM)
                .set(productBOM.deleteState, 1)
                .where(productBOM.id.in(ids))
                .execute();
    }
}
