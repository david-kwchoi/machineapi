package com.practice.machineservice.service;

import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.repository.MachineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MachineServiceImpl implements MachineService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    Validator validator;

    @Override
    public List<Machine> getAllMachine() {
        List<Machine> result = machineRepository.findAll();
        log.info("getAllMachine returns [{}] result", result.size());
        return result;
    }

    @Override
    public List<Machine> getMachinesByName(String name) {
        List<Machine> result = machineRepository.findAllByName(name);
        if (result == null)
            result = new ArrayList<>();
        log.info("getMachine returns [{}] result", result.size());
        return result;
    }

    @Override
    public Optional<Machine> getMachineById(Long id) {
        Optional<Machine> result = machineRepository.getById(id);
        log.info("getMachineById returns [{}]", result);
        return result;
    }

    @Override
    public Machine createMachine(Machine input) {
        Machine saved = machineRepository.save(input);
        log.info("createMachine returns [{}]", saved);
        return saved;
    }

    @Override
    public Machine updateMachine(Long id, Machine input) {
        Optional<Machine> current = machineRepository.getById(id);
        // all update requests must have a valid id
        if (!current.isPresent()) {
            throw new MachineNotFoundException(String.format("Machine id [%s] not found.", id));
        } else {
            // Only update set fields
            Machine result = current.get();
            if (input.getName() != null) result.setName(input.getName());
            if (input.getDescription() != null) result.setDescription(input.getDescription());
            if (input.getThroughputMins() != null) result.setThroughputMins(input.getThroughputMins());

            validateInput(result);
            Machine saved = machineRepository.save(result);
            log.info("updateMachine returns [{}]", result);
            return saved;
        }
    }

    private void validateInput(Machine input) {
        Set<ConstraintViolation<Machine>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
