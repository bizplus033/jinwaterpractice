package com.example.jinwaterpractice.product.stock;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jinwaterpractice.product.QProduct.product;
import static com.example.jinwaterpractice.product.stock.QProductStock.productStock;

@Repository
@RequiredArgsConstructor
public class DslProductStockRepositoryImpl implements DslProductStockRepository{
    private final JPAQueryFactory query;

    @Override
    public Page<ProductStock> findProductStockBySearchKeyword(String productCode, String productName, Pageable pageable) {
        List<ProductStock> content = query.selectFrom(productStock)
                .join(productStock.product, product)
                .where(product.deleteState.eq(0)
                        .and(likeProductCodeIgnoreCase(productCode))
                        .and(likeProductNameIgnoreCase(productName))
                ).orderBy(product.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long total = query.select(productStock.count())
                .from(productStock)
                .join(productStock.product, product)
                .where(product.deleteState.eq(0)
                        .and(likeProductCodeIgnoreCase(productCode))
                        .and(likeProductNameIgnoreCase(productName))
                )
                .fetchOne();

        if (total == null) total = 0L;
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 역시 단건 업데이트는 변경감지로 해결하자 이걸로 뭔가 하려고 하지말고
     * */
    @Override
    public void updateProductStock(Long productStockId, Integer amount) {
        query.update(productStock)
                .set(productStock.stock, productStock.stock.add(amount))
                .where(productStock.id.eq(productStockId))
                .execute();
    }

    private BooleanExpression likeProductCodeIgnoreCase(String productCode) {
        return product.code.likeIgnoreCase("*" + ((productCode == null) ? "" : productCode) + "%");
    }

    private BooleanExpression likeProductNameIgnoreCase(String productName) {
        return product.name.likeIgnoreCase("*" + ((productName == null) ? "" : productName) + "%");
    }
}
