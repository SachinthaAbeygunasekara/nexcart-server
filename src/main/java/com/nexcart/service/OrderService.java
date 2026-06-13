package com.nexcart.service;

import com.nexcart.dto.order.CheckoutRequest;
import com.nexcart.dto.order.OrderResponse;
import com.nexcart.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse checkout(
            String username,
            CheckoutRequest request
    );

    List<OrderResponse> getCustomerOrders(
            String username
    );

    List<OrderResponse> getAllOrders();

    OrderResponse updateStatus(
            Long orderId,
            OrderStatus status
    );

    List<OrderResponse> getOrders(
            Integer orderId,
            OrderStatus status,
            LocalDate date
    );
}
