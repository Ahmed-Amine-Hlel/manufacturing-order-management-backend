package com.manufacturing.backend.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeePayload(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Position is required")
        EmployeePosition position,

        @NotNull(message = "Machine ID is required")
        Long machineId
) {}
