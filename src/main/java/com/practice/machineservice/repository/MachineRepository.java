package com.practice.machineservice.repository;

import com.practice.machineservice.model.Machine;
import org.springframework.data.repository.CrudRepository;

public interface MachineRepository extends CrudRepository<Machine, Long> {
    Machine findByName(String anyString);
}
