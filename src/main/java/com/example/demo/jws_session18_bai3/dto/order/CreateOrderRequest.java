package com.example.demo.jws_session18_bai3.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderRequest(
        @NotEmpty List<@Valid OrderItemRequest> items
) {
}
