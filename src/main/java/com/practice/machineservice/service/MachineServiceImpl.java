package com.practice.machineservice.service;

import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl implements MachineService {
    @Autowired
    MachineRepository machineRepository;

    @Override
    public Machine getMachine(String name) {
        Machine result = machineRepository.findByName(name);
        if (result == null)
            throw new MachineNotFoundException(String.format("Machine [%s] not found.", name));
        return result;
    }
}
