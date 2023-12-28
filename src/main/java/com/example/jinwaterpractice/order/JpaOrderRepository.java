package com.example.jinwaterpractice.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long>, DslOrderRepository {

}
