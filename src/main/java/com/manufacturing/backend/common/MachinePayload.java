package com.manufacturing.backend.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record MachinePayload(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Status is required")
        MachineStatus status,

        LocalDateTime lastMaintenanceDate
) {}
