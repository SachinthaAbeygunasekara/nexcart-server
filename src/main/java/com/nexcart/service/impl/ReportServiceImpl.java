package com.nexcart.service.impl;

import com.nexcart.dto.report.MonthlySalesResponse;
import com.nexcart.dto.report.TopProductResponse;
import com.nexcart.entity.Order;
import com.nexcart.entity.OrderItem;
import com.nexcart.repository.OrderItemRepository;
import com.nexcart.repository.OrderRepository;
import com.nexcart.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<MonthlySalesResponse> getSalesReport() {

        Map<YearMonth, List<Order>> grouped =
                orderRepository.findDeliveredOrders()
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        order ->
                                                YearMonth.from(
                                                        order.getCreatedAt()
                                                )
                                )
                        );

        List<MonthlySalesResponse> result = new ArrayList<>();

        grouped.forEach((month, orders) -> {

            BigDecimal revenue =
                    orders.stream()
                            .map(Order::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

            result.add(
                    MonthlySalesResponse.builder()
                            .year(month.getYear())
                            .month(month.getMonthValue())
                            .totalOrders(
                                    (long) orders.size()
                            )
                            .totalRevenue(revenue)
                            .build()
            );
        });

        result.sort(
                Comparator.comparing(
                        MonthlySalesResponse::getYear
                ).thenComparing(
                        MonthlySalesResponse::getMonth
                )
        );

        return result;
    }

    @Override
    public List<TopProductResponse> getTopProducts() {

        List<OrderItem> items =
                orderItemRepository.findAll();

        Map<Long, Long> totals =
                new HashMap<>();

        Map<Long, String> names =
                new HashMap<>();

        for (OrderItem item : items) {

            Long productId =
                    item.getProduct().getId().longValue();

            totals.merge(
                    productId,
                    item.getQuantity().longValue(),
                    Long::sum
            );

            names.put(
                    productId,
                    item.getProduct().getName()
            );
        }

        return totals.entrySet()
                .stream()
                .sorted(
                        Map.Entry
                                .<Long, Long>comparingByValue()
                                .reversed()
                )
                .limit(10)
                .map(entry ->
                        TopProductResponse.builder()
                                .productId(entry.getKey())
                                .productName(
                                        names.get(
                                                entry.getKey()
                                        )
                                )
                                .totalSold(
                                        entry.getValue()
                                )
                                .build()
                )
                .toList();
    }
}