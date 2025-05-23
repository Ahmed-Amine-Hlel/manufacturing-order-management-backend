package com.manufacturing.backend.service;

import com.manufacturing.backend.model.Employee;
import com.manufacturing.backend.model.Machine;
import com.manufacturing.backend.model.ManufacturingOrder;
import com.manufacturing.backend.model.Product;
import com.manufacturing.backend.repository.EmployeeRepository;
import com.manufacturing.backend.repository.MachineRepository;
import com.manufacturing.backend.repository.ManufacturingOrderRepository;
import com.manufacturing.backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KpiService {
    private final ManufacturingOrderRepository manufacturingOrderRepository;
    private final MachineRepository machineRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;

    public Map<String, Long> getOrdersStatusCount() {
        List<ManufacturingOrder> orders = manufacturingOrderRepository.findAll();
        return orders.stream().collect(Collectors.groupingBy(
                o -> o.getStatus().name(), Collectors.counting()
        ));
    }

    public Map<String, Long> getMachineStatusCount() {
        List<Machine> machines = machineRepository.findAll();
        return machines.stream().collect(Collectors.groupingBy(
                m -> m.getStatus().name(), Collectors.counting()
        ));
    }

    public Map<String, Integer> getProductStockAlerts() {
        List<Product> products = productRepository.findAll();
        Map<String, Integer> alerts = new HashMap<>();
        for (Product p : products) {
            if (p.getStock() < 10) { 
                alerts.put(p.getName(), p.getStock());
            }
        }
        return alerts;
    }

    public Map<String, Long> getEmployeeWorkload() {
        List<Employee> employees = employeeRepository.findAll();
        Map<String, Long> workload = new HashMap<>();
        for (Employee e : employees) {
            workload.put(e.getName(), e.getEmployeeMachine() != null ? 1L : 0L);
        }
        return workload;
    }

    // Orders trend: number of orders per day (last 30 days)
    public Map<String, Long> getOrdersTrend() {
        List<ManufacturingOrder> orders = manufacturingOrderRepository.findAll();
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getDate().toLocalDate().toString(), Collectors.counting()
                ));
    }

    // Machine utilization: % of orders assigned to each machine (relative to total orders)
    public Map<String, Double> getMachineUtilization() {
        List<ManufacturingOrder> orders = manufacturingOrderRepository.findAll();
        List<Machine> machines = machineRepository.findAll();
        long totalOrders = orders.size();
        Map<String, Double> utilization = new HashMap<>();
        for (Machine m : machines) {
            long count = orders.stream().filter(o -> o.getMachine().getId().equals(m.getId())).count();
            double percent = totalOrders > 0 ? (count * 100.0 / totalOrders) : 0.0;
            utilization.put(m.getName(), percent);
        }
        return utilization;
    }
}
