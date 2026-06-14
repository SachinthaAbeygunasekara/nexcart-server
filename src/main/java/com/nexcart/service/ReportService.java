package com.nexcart.service;

import com.nexcart.dto.report.MonthlySalesResponse;
import com.nexcart.dto.report.TopProductResponse;

import java.util.List;

public interface ReportService {

    List<MonthlySalesResponse> getSalesReport();

    List<TopProductResponse> getTopProducts();
}