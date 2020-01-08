package com.practice.machineservice.service;

import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.repository.MachineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.*;

import static com.practice.machineservice.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MachineServiceImplTest {

    @InjectMocks
    private MachineServiceImpl machineService;

    @Mock
    private Validator validator;

    @Mock
    private MachineRepository machineRepository;

    @Test
    public void testGetAllMachines() {
        given(machineRepository.findAll()).willReturn(Arrays.asList(MACHINE, UPDATED_MACHINE));
        List<Machine> result = machineService.getAllMachine();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.equals(Arrays.asList(MACHINE, UPDATED_MACHINE))).isTrue();
    }

    @Test
    public void testGetAllMachinesWithEmptyResult() {
        given(machineRepository.findAll()).willReturn(new ArrayList<>());
        List<Machine> result = machineService.getAllMachine();

        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    public void testCreateMachine() {
        given(machineRepository.save(any(Machine.class))).willReturn(MACHINE);

        Machine actual = machineService.createMachine(UNSAVED_MACHINE);

        assertThat(actual.getId()).isEqualTo(MACHINE.getId());
        assertThat(actual.getName()).isEqualTo(MACHINE.getName());
        assertThat(actual.getDescription()).isEqualTo(MACHINE.getDescription());
        assertThat(actual.getThroughputMins()).isEqualTo(MACHINE.getThroughputMins());
    }

    @Test
    public void testUpdateMachineExisting() {
        given(machineRepository.getById(eq(ORIGINAL_ID))).willReturn(Optional.of(MACHINE));
        given(machineRepository.save(any(Machine.class))).willReturn(UPDATED_MACHINE);

        Machine actual = machineService.updateMachine(ORIGINAL_ID, UPDATED_MACHINE);

        assertThat(actual.getId()).isEqualTo(UPDATED_MACHINE.getId());
        assertThat(actual.getName()).isEqualTo(UPDATED_MACHINE.getName());
        assertThat(actual.getDescription()).isEqualTo(UPDATED_MACHINE.getDescription());
        assertThat(actual.getThroughputMins()).isEqualTo(UPDATED_MACHINE.getThroughputMins());
    }

    @Test
    public void testUpdateMachineNotExisting() {
        given(machineRepository.getById(eq(ORIGINAL_ID))).willReturn(Optional.empty());

        assertThrows(MachineNotFoundException.class, () -> {
            machineService.updateMachine(ORIGINAL_ID, MACHINE);
        });
    }

    @Test
    public void testGetMachineById() {
        given(machineRepository.getById(1L)).willReturn(Optional.of(MACHINE));

        Optional<Machine> actualOpt = machineService.getMachineById(1L);

        assertThat(actualOpt.isPresent()).isTrue();
        Machine actual = actualOpt.get();
        assertThat(actual.getId()).isEqualTo(MACHINE.getId());
        assertThat(actual.getName()).isEqualTo(MACHINE.getName());
        assertThat(actual.getDescription()).isEqualTo(MACHINE.getDescription());
        assertThat(actual.getThroughputMins()).isEqualTo(MACHINE.getThroughputMins());
    }

    @Test
    public void testGetMachineByIdNotFound() {
        given(machineRepository.getById(anyLong())).willReturn(Optional.empty());

        Optional<Machine> actual = machineService.getMachineById(1L);
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    public void testGetMachine() {
        given(machineRepository.findAllByName("plating1"))
                .willReturn(Collections.singletonList(MACHINE));

        List<Machine> results = machineService.getMachinesByName("plating1");
        assertThat(results.size()).isEqualTo(1);

        Machine actual = results.get(0);
        assertThat(actual.getId()).isEqualTo(MACHINE.getId());
        assertThat(actual.getName()).isEqualTo(MACHINE.getName());
        assertThat(actual.getDescription()).isEqualTo(MACHINE.getDescription());
        assertThat(actual.getThroughputMins()).isEqualTo(MACHINE.getThroughputMins());
    }

    @Test
    public void testGetMachineByNameNotFound() {
        given(machineRepository.findAllByName(anyString())).willReturn(null);

        List<Machine> machines = machineService.getMachinesByName("abcasdasf");
        assertThat(machines.size()).isZero();
    }


}