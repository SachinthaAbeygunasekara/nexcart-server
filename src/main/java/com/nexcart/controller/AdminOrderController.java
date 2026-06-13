package com.nexcart.controller;

import com.nexcart.dto.order.OrderResponse;
import com.nexcart.enums.OrderStatus;
import com.nexcart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrders(
            @RequestParam(required = false) Integer orderId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) LocalDate date
    ) {
        return orderService.getOrders(
                orderId,
                status,
                date
        );
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return orderService.updateStatus(
                id,
                status
        );
    }
}
