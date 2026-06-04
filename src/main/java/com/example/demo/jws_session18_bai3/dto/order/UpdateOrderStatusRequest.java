package com.example.demo.jws_session18_bai3.dto.order;

import com.example.demo.jws_session18_bai3.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull OrderStatus status
) {
}
