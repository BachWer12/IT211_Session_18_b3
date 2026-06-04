package com.example.demo.jws_session18_bai3.repository;

import com.example.demo.jws_session18_bai3.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
