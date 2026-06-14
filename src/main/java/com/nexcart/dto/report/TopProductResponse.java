package com.nexcart.dto.report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopProductResponse {

    private Long productId;

    private String productName;

    private Long totalSold;
}