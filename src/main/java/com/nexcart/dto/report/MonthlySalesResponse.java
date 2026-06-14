package com.nexcart.dto.report;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MonthlySalesResponse {

    private Integer year;

    private Integer month;

    private Long totalOrders;

    private BigDecimal totalRevenue;
}