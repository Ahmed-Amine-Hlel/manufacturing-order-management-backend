package com.manufacturing.backend.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateManufacturingOrderPayload(
        @NotNull(message = "Project name is required")
        @NotBlank(message = "Project name is required")
        String project,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Date is required")
        LocalDateTime date,

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Machine ID is required")
        Long machineId
) {}
