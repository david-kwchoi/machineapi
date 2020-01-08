package com.practice.machineservice.dto;

import com.practice.machineservice.model.Machine;

import java.util.List;

public class Machines {
    private List<Machine> machines;

    public Machines() {
    }

    public Machines(List<Machine> machines) {
        this.machines = machines;
    }

    public List<Machine> getMachines() {
        return machines;
    }
}
