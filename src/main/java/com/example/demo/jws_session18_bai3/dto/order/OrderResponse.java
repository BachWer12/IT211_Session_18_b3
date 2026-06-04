package com.example.demo.jws_session18_bai3.dto.order;

import com.example.demo.jws_session18_bai3.entity.Order;
import com.example.demo.jws_session18_bai3.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        String username,
        LocalDateTime createdDate,
        OrderStatus status,
        BigDecimal totalMoney,
        List<OrderItemResponse> items
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getUsername(),
                order.getCreatedDate(),
                order.getStatus(),
                order.getTotalMoney(),
                order.getItems().stream()
                        .map(OrderItemResponse::from)
                        .toList()
        );
    }
}
