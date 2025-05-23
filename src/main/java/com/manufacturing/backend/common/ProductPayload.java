package com.manufacturing.backend.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductPayload(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        ProductType type,

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock cannot be less than 0")
        Integer stock,

        @NotNull(message = "Supplier is required")
        @NotBlank(message = "Supplier is required")
        String supplier
) {}
