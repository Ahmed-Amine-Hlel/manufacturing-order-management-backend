package com.manufacturing.backend.controller;

import com.manufacturing.backend.service.KpiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/kpis")
@AllArgsConstructor
public class KpiController {
    private final KpiService kpiService;

    @GetMapping({"/orders-status-count", "/orders-status-count/"})
    public ResponseEntity<Map<String, Long>> getOrdersStatusCount() {
        return ResponseEntity.ok(kpiService.getOrdersStatusCount());
    }

    @GetMapping({"/machine-status-count", "/machine-status-count/"})
    public ResponseEntity<Map<String, Long>> getMachineStatusCount() {
        return ResponseEntity.ok(kpiService.getMachineStatusCount());
    }

    @GetMapping({"/product-stock-alerts", "/product-stock-alerts/"})
    public ResponseEntity<Map<String, Integer>> getProductStockAlerts() {
        return ResponseEntity.ok(kpiService.getProductStockAlerts());
    }

    @GetMapping({"/employee-workload", "/employee-workload/"})
    public ResponseEntity<Map<String, Long>> getEmployeeWorkload() {
        return ResponseEntity.ok(kpiService.getEmployeeWorkload());
    }

    @GetMapping({"/orders-trend", "/orders-trend/"})
    public ResponseEntity<Map<String, Long>> getOrdersTrend() {
        return ResponseEntity.ok(kpiService.getOrdersTrend());
    }

    @GetMapping({"/machine-utilization", "/machine-utilization/"})
    public ResponseEntity<Map<String, Double>> getMachineUtilization() {
        return ResponseEntity.ok(kpiService.getMachineUtilization());
    }
}
