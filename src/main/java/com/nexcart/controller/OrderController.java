package com.nexcart.controller;

import com.nexcart.dto.order.CheckoutRequest;
import com.nexcart.dto.order.OrderResponse;
import com.nexcart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody CheckoutRequest request
    ) {
        return ResponseEntity.ok(
                orderService.checkout(
                        user.getUsername(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> myOrders(
            @AuthenticationPrincipal UserDetails user
    ) {
        return ResponseEntity.ok(
                orderService.getCustomerOrders(
                        user.getUsername()
                )
        );
    }
}