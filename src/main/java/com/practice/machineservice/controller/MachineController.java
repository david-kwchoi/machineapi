package com.practice.machineservice.controller;


import com.practice.machineservice.dto.Machines;
import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.service.MachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class MachineController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MachineService machineService;

    @GetMapping("/machines/{name}")
    private Machines getMachinesByName(@PathVariable String name) {
        log.info("Received getMachinesByName request [{}]", name);
        return new Machines(machineService.getMachinesByName(name));
    }

    @GetMapping("/machine/{id}")
    private Machine getMachineById(@PathVariable Long id) {
        log.info("Received getMachineById request [{}}]", id);
        Optional<Machine> machineOptional = machineService.getMachineById(id);
        if (machineOptional.isPresent())
            return machineOptional.get();
        else
            throw new MachineNotFoundException(String.format("Machine id [%s] not found.", id));
    }

    @GetMapping("/machines")
    private Machines getAllMachines() {
        log.info("Received getAllMachines request");
        return new Machines(machineService.getAllMachine());
    }

    @PostMapping("/machine/add")
    @ResponseStatus(HttpStatus.CREATED)
    private Machine createMachine(@RequestBody @Valid Machine input) {

        log.info("Received createMachine request [{}]", input);
        return machineService.createMachine(input);
    }

    @PostMapping("/machine/{id}")
    private ResponseEntity<Machine> updateMachine(@PathVariable Long id, @RequestBody Machine input) {
        log.info("Received updateMachine request for id [{}] input [{}]", id, input);
        if (!machineService.getMachineById(id).isPresent()) {
            log.warn("Machine id [{}] not found", id);
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(machineService.updateMachine(id, input));
        }
    }

    @PutMapping("/machine/{id}")
    private ResponseEntity<Machine> upsertMachine(@PathVariable Long id, @RequestBody @Valid Machine input) {
        log.info("Received upsertMachine request for id [{}] input [{}]", id, input);
        if (!machineService.getMachineById(id).isPresent()) {
            log.warn("Machine id [{}] not found. Attempting to createMachine with input[{}]", id, input);
            return new ResponseEntity<>(machineService.createMachine(input), HttpStatus.CREATED);
        } else {
            return ResponseEntity.ok(machineService.updateMachine(id, input));
        }
    }

}
