package com.example.demo.jws_session18_bai3.repository;

import com.example.demo.jws_session18_bai3.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "items", "items.product"})
    @Query("select distinct o from OrderEntity o where o.user.username = :username order by o.createdDate desc")
    List<Order> findByUsernameWithDetails(@Param("username") String username);

    @EntityGraph(attributePaths = {"user", "items", "items.product"})
    @Query("select distinct o from OrderEntity o order by o.createdDate desc")
    List<Order> findAllWithDetails();

    @EntityGraph(attributePaths = {"user", "items", "items.product"})
    @Query("select o from OrderEntity o where o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}
