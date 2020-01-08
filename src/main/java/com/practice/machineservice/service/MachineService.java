package com.practice.machineservice.service;

import com.practice.machineservice.model.Machine;

import java.util.List;
import java.util.Optional;

public interface MachineService {
    List<Machine> getAllMachine();

    List<Machine> getMachinesByName(String name);

    Optional<Machine> getMachineById(Long id);

    Machine createMachine(Machine input);

    Machine updateMachine(Long id, Machine input);
}
