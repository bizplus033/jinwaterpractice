package com.example.jinwaterpractice.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jinwaterpractice.product.QProduct.product;
@Repository
@RequiredArgsConstructor
public class DslProductRepositoryImpl implements DslProductRepository{
    private final JPAQueryFactory query;
    @Override
    public Page<Product> findProductAllBySearchKeyword(String code, String name, Pageable pageable) {
        List<Product> content = query.selectFrom(product)
                .where(product.deleteState.eq(0)
                        .and(likeCodeIgnoreCase(code))
                        .and(likeNameIgnoreCase(name))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query.select(product.count())
                .from(product)
                .where(product.deleteState.eq(0)
                        .and(likeCodeIgnoreCase(code))
                        .and(likeNameIgnoreCase(name))
                )
                .fetchOne();

        if (total == null) total = 0L;
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void updateDeleteStateOfProduct(Long[] productIds) {
        query.update(product)
                .set(product.deleteState , 1)
                .where(product.id.in(productIds))
                .execute();

        // todo 벌크 연산이니까 Persistent Context 비워줘야 할까? 일단 그냥 해보자
    }

    private BooleanExpression likeNameIgnoreCase(String name) {
        return product.name.likeIgnoreCase("%" + ((name == null) ? "" : name) + "%");
    }

    private BooleanExpression likeCodeIgnoreCase(String code) {
        return product.code.likeIgnoreCase("%" + ((code == null) ? "" : code) + "%");
    }
}
