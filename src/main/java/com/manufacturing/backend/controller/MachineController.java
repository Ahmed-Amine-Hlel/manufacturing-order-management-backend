package com.manufacturing.backend.controller;


import com.manufacturing.backend.common.MachinePayload;
import com.manufacturing.backend.model.Machine;
import com.manufacturing.backend.service.MachineService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/machines")
@AllArgsConstructor
public class MachineController {

    private final MachineService machineService;


    /**
     * Retrieve all machines.
     *
     * @return List of all machines
     */
    @GetMapping("/")
    public ResponseEntity<List<Machine>> getAllMachines() {
        return ResponseEntity.ok(this.machineService.findAllMachines());
    }

    /**
     * Create a new machine.
     *
     * @param payload Payload to create
     * @return Created machine
     */
    @PostMapping("/")
    public ResponseEntity<Machine> createMachine(
            @Valid @RequestBody MachinePayload payload
    ) {
        Machine savedMachine = this.machineService.saveMachine(payload);
        return ResponseEntity.ok(savedMachine);
    }

    /**
     * Update a machine by its ID.
     *
     * @param id Machine ID
     * @return ResponseEntity containing the updated machine if found, or 404 Not Found
     */

    @PutMapping("/{id}")
    public ResponseEntity<Machine> updateMachine(
            @PathVariable Long id,
            @Valid @RequestBody MachinePayload payload
    ) {
        return this.machineService.updateMachine(id, payload)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a machine by its ID.
     *
     * @param id Machine ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMachine(@PathVariable Long id) {
        this.machineService.deleteMachine(id);
        return ResponseEntity.ok("Machine deleted successfully");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();
        if (message.contains("MachineStatus")) {
            return Map.of("status", "Invalid machine status. Must be one of: OPERATIONAL, UNDER_MAINTENANCE, OUT_OF_ORDER, IDLE, DECOMMISSIONED");
        }
        return Map.of("error", "Invalid request body: " + message);
    }
}
