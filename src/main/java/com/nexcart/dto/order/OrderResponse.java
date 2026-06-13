package com.nexcart.dto.order;

import com.nexcart.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private String deliveryAddress;

    private String phoneNumber;

    private List<OrderItemResponse> items;
}