package com.manufacturing.backend.controller;

import com.manufacturing.backend.common.EmployeePayload;
import com.manufacturing.backend.model.Employee;
import com.manufacturing.backend.service.EmployeeService;
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
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Retrieve all employees.
     *
     * @return List of all employees
     */
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.findAllEmployees());
    }

    /**
     * Create a new employee.
     *
     * @param payload Payload to create
     * @return Created employee
     */
    @PostMapping("/")
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody EmployeePayload payload
    ) {
        Employee savedEmployee = this.employeeService.saveEmployee(payload);
        return ResponseEntity.ok(savedEmployee);
    }

    /**
     * Update an employee by its ID.
     *
     * @param id Employee ID
     * @return ResponseEntity containing the updated employee if found, or 404 Not Found
     */

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeePayload payload
    ) {
        return this.employeeService.updateEmployee(id, payload)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete an employee by its ID.
     *
     * @param id Employee ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
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
        if (message.contains("EmployeePosition")) {
            return Map.of("position", "Invalid employee position. Must be one of: OPERATOR, TECHNICIAN, SUPERVISOR, QUALITY_INSPECTOR, PRODUCTION_MANAGER, ASSEMBLER, MAINTENANCE_ENGINEER, LOGISTICS_COORDINATOR, SAFETY_OFFICER, SHIFT_LEADER, TOOLMAKER");
        }
        return Map.of("error", "Invalid request body: " + message);
    }
}
