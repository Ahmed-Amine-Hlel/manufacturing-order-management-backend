package com.manufacturing.backend.controller;


import com.manufacturing.backend.common.CreateManufacturingOrderPayload;
import com.manufacturing.backend.common.ManufacturingOrderStatus;
import com.manufacturing.backend.common.UpdateManufacturingOrderPayload;
import com.manufacturing.backend.model.ManufacturingOrder;
import com.manufacturing.backend.service.ManufacturingOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manufacturing-orders")
@AllArgsConstructor
public class ManufacturingOrderController {
    private final ManufacturingOrderService manufacturingOrderService;

    /**
     * Retrieve all manufacturing orders.
     *
     * @return List of all manufacturing orders
     */
    @GetMapping("/")
    public ResponseEntity<List<ManufacturingOrder>> getAllOrders() {
        List<ManufacturingOrder> orders = this.manufacturingOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieve a manufacturing order by its ID.
     *
     * @param id Manufacturing order ID
     * @return ResponseEntity containing the order if found, or 404 Not Found
     */
    @GetMapping(path = {"/{id}", "/{id}/"})
    public ResponseEntity<ManufacturingOrder> getOrderById(
            @PathVariable String id
    ) {
        Long orderId = Long.parseLong(id);
        return this.manufacturingOrderService.getOrderById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new manufacturing order.
     *
     * @param payload Payload to create
     * @return Created manufacturing order
     */
    @PostMapping("/")
    public ResponseEntity<ManufacturingOrder> createOrder(
            @Valid
            @RequestBody
            CreateManufacturingOrderPayload payload
    ) {
        ManufacturingOrder savedOrder = this.manufacturingOrderService.createOrder(payload);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping(path = {"/{id}", "/{id}/"})
    public ResponseEntity<ManufacturingOrder> updateOrder(
            @PathVariable String id,
            @Valid @RequestBody UpdateManufacturingOrderPayload payload) {
        Long orderId = Long.parseLong(id);
        ManufacturingOrder updatedOrder = this.manufacturingOrderService.updateOrder(orderId, payload);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping(path = {"/{id}", "/{id}/"})
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        Long orderId = Long.parseLong(id);
        this.manufacturingOrderService.deleteOrder(orderId);
        return ResponseEntity.ok("Manufacturing order deleted successfully.");
    }

    @PutMapping(path = {"/{id}/status", "/{id}/status/"})
    public ResponseEntity<ManufacturingOrder> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody
            @NotNull(message = "Status is required")
            ManufacturingOrderStatus status
    ) {
        Long orderId = Long.parseLong(id);
        ManufacturingOrder updatedOrder = this.manufacturingOrderService.updateStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message.contains("ManufacturingOrderStatus")) {
            String validStatuses = String.join(", ", java.util.Arrays.stream(com.manufacturing.backend.common.ManufacturingOrderStatus.values()).map(Enum::name).toList());
            return Map.of("type", "Invalid manufacturing order status. Must be one of: " + validStatuses);
        }
        return Map.of("error", "Invalid request body: " + message);
    }
}
