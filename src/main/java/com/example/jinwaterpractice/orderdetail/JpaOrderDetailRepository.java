package com.example.jinwaterpractice.orderdetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderDetailRepository extends JpaRepository<OrderDetail, Long>, DslOrderDetailRepository {
}
