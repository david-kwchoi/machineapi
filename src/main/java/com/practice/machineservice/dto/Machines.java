package com.practice.machineservice.dto;

import com.practice.machineservice.model.Machine;

import java.util.List;

public class Machines {
    public Machines() {
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    private List<Machine> machines;

    public Machines(List<Machine> machines) {
        this.machines = machines;
    }
}
