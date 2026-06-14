package com.nexcart.service.impl;

import com.nexcart.dto.dashboard.DashboardStatsResponse;
import com.nexcart.entity.Order;
import com.nexcart.enums.OrderStatus;
import com.nexcart.repository.OrderRepository;
import com.nexcart.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;

    @Override
    public DashboardStatsResponse getStats() {

        BigDecimal revenue = orderRepository.findAll()
                .stream()
                .filter(order ->
                        order.getStatus() == OrderStatus.DELIVERED
                )
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardStatsResponse.builder()
                .totalOrders(orderRepository.count())
                .pendingOrders(
                        orderRepository.countByStatus(
                                OrderStatus.PENDING
                        )
                )
                .confirmedOrders(
                        orderRepository.countByStatus(
                                OrderStatus.CONFIRMED
                        )
                )
                .deliveredOrders(
                        orderRepository.countByStatus(
                                OrderStatus.DELIVERED
                        )
                )
                .cancelledOrders(
                        orderRepository.countByStatus(
                                OrderStatus.CANCELLED
                        )
                )
                .processingOrders(
                        orderRepository.countByStatus(
                                OrderStatus.PROCESSING
                        )
                )
                .shippedOrders(
                        orderRepository.countByStatus(
                                OrderStatus.SHIPPED
                        )
                )
                .totalRevenue(revenue)
                .build();
    }
}