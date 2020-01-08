package com.practice.machineservice.controller;

import com.practice.machineservice.dto.Machines;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MachineController {
    @Autowired
    MachineService machineService;

    @GetMapping("/machines/{name}")
    private Machines getMachinesByName(@PathVariable String name) {
        return new Machines(machineService.getMachine(name));
    }

    @GetMapping("/machine/{id}")
    private Machine getMachineById(@PathVariable Long id) {
        return machineService.getMachineById(id);
    }

    @GetMapping("/machines")
    private Machines getMachines() {
        return new Machines(machineService.getAllMachine());
    }

    @PostMapping("/machine/add")
    @ResponseStatus(HttpStatus.CREATED)
    private Machine createMachine(@RequestBody @Valid Machine input) {
        return machineService.createMachine(input);
    }

    @PostMapping("/machine/{id}")
    private Machine updateMachine(@PathVariable Long id, @RequestBody Machine input) {
        return machineService.updateMachine(id, input);
    }

    @PutMapping("/machine/{id}")
    private ResponseEntity<Machine> replaceEmployee(@PathVariable Long id, @RequestBody @Valid Machine input) {
        if (machineService.getMachineById(id) == null)
            return new ResponseEntity<>(machineService.createMachine(new Machine(id, input)), HttpStatus.CREATED);
        else {
            return ResponseEntity.ok(machineService.updateMachine(id, input));
        }
    }

}
