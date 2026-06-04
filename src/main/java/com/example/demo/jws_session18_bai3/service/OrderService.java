package com.example.demo.jws_session18_bai3.service;

import com.example.demo.jws_session18_bai3.dto.order.CreateOrderRequest;
import com.example.demo.jws_session18_bai3.dto.order.OrderItemRequest;
import com.example.demo.jws_session18_bai3.dto.order.OrderResponse;
import com.example.demo.jws_session18_bai3.dto.order.UpdateOrderStatusRequest;
import com.example.demo.jws_session18_bai3.entity.Order;
import com.example.demo.jws_session18_bai3.entity.OrderItem;
import com.example.demo.jws_session18_bai3.entity.OrderStatus;
import com.example.demo.jws_session18_bai3.entity.Product;
import com.example.demo.jws_session18_bai3.entity.User;
import com.example.demo.jws_session18_bai3.repository.OrderRepository;
import com.example.demo.jws_session18_bai3.repository.ProductRepository;
import com.example.demo.jws_session18_bai3.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse createOrder(String username, CreateOrderRequest request) {
        User user = getUser(username);
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .totalMoney(BigDecimal.ZERO)
                .build();

        BigDecimal totalMoney = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Product not found: " + itemRequest.productId()
                    ));

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalMoney = totalMoney.add(itemTotal);

            order.addItem(OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .priceBuy(product.getPrice())
                    .build());
        }

        order.setTotalMoney(totalMoney);
        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(String username) {
        return orderRepository.findByUsernameWithDetails(username)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllWithDetails()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional
    public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id));
        order.setStatus(request.status());
        return OrderResponse.from(order);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + username));
    }
}
