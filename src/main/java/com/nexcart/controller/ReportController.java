package com.nexcart.controller;

import com.nexcart.dto.report.MonthlySalesResponse;
import com.nexcart.dto.report.TopProductResponse;
import com.nexcart.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    public List<MonthlySalesResponse> getSalesReport() {
        return reportService.getSalesReport();
    }

    @GetMapping("/top-products")
    public List<TopProductResponse> getTopProducts() {
        return reportService.getTopProducts();
    }
}