package com.nexcart.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardStatsResponse {

    private long totalOrders;

    private long pendingOrders;

    private long confirmedOrders;

    private long deliveredOrders;

    private long cancelledOrders;

    private long processingOrders;

    private long shippedOrders;

    private BigDecimal totalRevenue;
}