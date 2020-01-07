package com.practice.machineservice.controller;

import com.practice.machineservice.model.Machine;
import com.practice.machineservice.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MachineController {
    @Autowired
    MachineService machineService;

    @GetMapping("/machine/{name}")
    private Machine getMachine(@PathVariable String name) {
        return machineService.getMachine(name);
    }


    @PostMapping("/machine")
    private Machine createMachine(Machine input) {
        return machineService.createMachine(input);
    }

}
