package com.example.jinwaterpractice.main;

import com.querydsl.jpa.impl.JPAQueryFactory;
import net.bytebuddy.matcher.ElementMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {return new JPAQueryFactory(entityManager);}

}
