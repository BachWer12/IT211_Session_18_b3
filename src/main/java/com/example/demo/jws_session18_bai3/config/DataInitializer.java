package com.example.demo.jws_session18_bai3.config;

import com.example.demo.jws_session18_bai3.entity.Product;
import com.example.demo.jws_session18_bai3.entity.Role;
import com.example.demo.jws_session18_bai3.entity.User;
import com.example.demo.jws_session18_bai3.repository.ProductRepository;
import com.example.demo.jws_session18_bai3.repository.UserRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfMissing("customer", "customer123", Role.CUSTOMER);
        createUserIfMissing("staff", "staff123", Role.STAFF);
        createUserIfMissing("admin", "admin123", Role.ADMIN);

        if (productRepository.count() == 0) {
            productRepository.save(Product.builder()
                    .name("Keyboard")
                    .price(new BigDecimal("350000"))
                    .build());
            productRepository.save(Product.builder()
                    .name("Mouse")
                    .price(new BigDecimal("180000"))
                    .build());
            productRepository.save(Product.builder()
                    .name("Headphone")
                    .price(new BigDecimal("520000"))
                    .build());
        }
    }

    private void createUserIfMissing(String username, String password, Role role) {
        if (!userRepository.existsByUsername(username)) {
            userRepository.save(User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build());
        }
    }
}
