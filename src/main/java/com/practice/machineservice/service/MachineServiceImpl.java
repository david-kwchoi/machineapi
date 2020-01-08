package com.practice.machineservice.service;

import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MachineServiceImpl implements MachineService {
    @Autowired
    MachineRepository machineRepository;

    @Autowired
    Validator validator;

    @Override
    public List<Machine> getAllMachine() {
        return machineRepository.findAll();
    }

    @Override
    public List<Machine> getMachine(String name) {
        List<Machine> result = machineRepository.findAllByName(name);
        if (result == null || result.isEmpty())
            throw new MachineNotFoundException(String.format("Machine [%s] not found.", name));
        return result;
    }

    @Override
    public Machine getMachineById(Long id) {
        Optional<Machine> result = machineRepository.getById(id);
        if (!result.isPresent())
            throw new MachineNotFoundException(String.format("Machine id [%s] not found.", id));
        return result.get();
    }

    @Override
    public Machine createMachine(Machine input) {
        return machineRepository.save(input);
    }

    @Override
    public Machine updateMachine(Long id, Machine input) {
        Optional<Machine> current = machineRepository.getById(id);

        if (!current.isPresent()) {
            validateInput(input);
            return createMachine(new Machine(id, input));
        } else {
            // Only update set fields
            Machine result = current.get();
            if (input.getName() != null) result.setName(input.getName());
            if (input.getDescription() != null) result.setDescription(input.getDescription());
            if (input.getThroughputMins() != null) result.setThroughputMins(input.getThroughputMins());

            validateInput(result);
            return machineRepository.save(result);
        }
    }

    private void validateInput(Machine input) {
        Set<ConstraintViolation<Machine>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
