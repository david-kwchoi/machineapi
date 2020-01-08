package com.practice.machineservice.repository;

import com.practice.machineservice.model.Machine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends CrudRepository<Machine, Long> {
    List<Machine> findAllByName(String name);

    Optional<Machine> getById(Long id);

    List<Machine> findAll();
}
