package com.practice.machineservice.service;

import com.practice.machineservice.model.Machine;

public interface MachineService {
    Machine getMachine(String name);

    Machine createMachine(Machine input);
}
