package com.manufacturing.backend.service;

import com.manufacturing.backend.common.EmployeePayload;
import com.manufacturing.backend.model.Employee;
import com.manufacturing.backend.model.Machine;
import com.manufacturing.backend.repository.EmployeeRepository;
import com.manufacturing.backend.repository.MachineRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MachineRepository machineRepository;

    public List<Employee> findAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Transactional
    public Employee saveEmployee(EmployeePayload payload) {

        Machine machine = machineRepository.findById(payload.machineId())
                .orElseThrow(() -> new IllegalArgumentException("Machine not found with ID: " + payload.machineId()));


        Employee employee = new Employee();
        employee.setName(payload.name());
        employee.setPosition(payload.position());
        employee.setEmployeeMachine(machine);

        return this.employeeRepository.save(employee);
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return this.employeeRepository.findById(id);
    }

    @Transactional
    public Optional<Employee> updateEmployee(Long id, EmployeePayload payload) {

        Machine machine = machineRepository.findById(payload.machineId())
                .orElseThrow(() -> new IllegalArgumentException("Machine not found with ID: " + payload.machineId()));


        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(id);
        employeeToUpdate.setName(payload.name());
        employeeToUpdate.setPosition(payload.position());
        employeeToUpdate.setEmployeeMachine(machine);

        return this.employeeRepository.findById(id)
                .map(existingEmployee -> {
                    employeeToUpdate.setId(id); 
                    return this.employeeRepository.save(employeeToUpdate);
                });
    }

    public void deleteEmployee(Long id) {
        this.employeeRepository.deleteById(id);
    }
}
