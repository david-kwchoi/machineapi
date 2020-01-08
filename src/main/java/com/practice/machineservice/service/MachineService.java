package com.practice.machineservice.service;

import com.practice.machineservice.model.Machine;

import java.util.List;

public interface MachineService {
    List<Machine> getAllMachine();

    List<Machine> getMachine(String name);

    Machine getMachineById(Long id);

    Machine createMachine(Machine input);

    Machine updateMachine(Long id, Machine input);
}
