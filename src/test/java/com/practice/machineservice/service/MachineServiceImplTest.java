package com.practice.machineservice.service;

import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.repository.MachineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MachineServiceImplTest {

    @InjectMocks
    private MachineServiceImpl machineService;

    @Mock
    private MachineRepository machineRepository;

    @Test
    public void getMachine_returnMachineInfo() {
        given(machineRepository.findByName("plating1"))
                .willReturn(new Machine("plating1", "Gold plating machine #1", 10));

        Machine actual = machineService.getMachine("plating1");

        assertThat(actual.getName()).isEqualTo("plating1");
        assertThat(actual.getDescription()).isEqualTo("Gold plating machine #1");
        assertThat(actual.getThroughputMins()).isEqualTo(10);
    }

    @Test
    public void getMachine_whenMachineNotFound() {
        given(machineRepository.findByName(anyString())).willReturn(null);

        assertThrows(MachineNotFoundException.class, () -> {
            machineService.getMachine("abcasdasf");
        });
    }


}