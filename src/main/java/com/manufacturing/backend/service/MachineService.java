package com.manufacturing.backend.service;

import com.manufacturing.backend.common.MachinePayload;
import com.manufacturing.backend.model.Machine;
import com.manufacturing.backend.repository.MachineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    /**
     * Retrieve all machines.
     *
     * @return List of all machines
     */
    public List<Machine> findAllMachines() {
        return this.machineRepository.findAll();
    }

    /**
     * Save a new or updated product.
     *
     * @param payload Payload to save of type ProductPayload
     * @return Saved product
     */
    public Machine saveMachine(MachinePayload payload) {
        Machine machine = new Machine();
        machine.setName(payload.name());
        machine.setStatus(payload.status());
        if (payload.lastMaintenanceDate() != null) {
            machine.setLastMaintenanceDate(payload.lastMaintenanceDate());
        } else {
            machine.setLastMaintenanceDate(null);
        }

        return this.machineRepository.save(machine);
    }


    /**
     * Find a machine by its ID.
     *
     * @param id Machine ID
     * @return Optional containing the machine if found, empty otherwise
     */
    public Optional<Machine> findMachineById(Long id) {
        return this.machineRepository.findById(id);
    }


    /**
     * Update an existing machine.
     *
     * @param id      ID of the machine to update
     * @param machine Updated machine data
     * @return Optional containing the updated product if found, empty otherwise
     */
    public Optional<Machine> updateMachine(Long id, MachinePayload machine) {
        Machine machineToUpdate = new Machine();
        machineToUpdate.setId(id);
        machineToUpdate.setName(machine.name());
        machineToUpdate.setStatus(machine.status());
        if (machine.lastMaintenanceDate() != null) {
            machineToUpdate.setLastMaintenanceDate(machine.lastMaintenanceDate());
        } else {
            machineToUpdate.setLastMaintenanceDate(null);
        }

        return this.machineRepository.findById(id)
                .map(existingProduct -> {
                    machineToUpdate.setId(id); 
                    return this.machineRepository.save(machineToUpdate);
                });
    }

    /**
     * Delete a machine by its ID.
     *
     * @param id machine ID to delete
     */
    public void deleteMachine(Long id) {
        this.machineRepository.deleteById(id);
    }

}
