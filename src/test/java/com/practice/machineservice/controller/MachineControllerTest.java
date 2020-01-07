package com.practice.machineservice.controller;


import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class MachineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MachineService machineService;

    @Test
    public void testGetMachineByName() throws Exception {
        given(machineService.getMachine("plating1")).willReturn(new Machine("plating1", "Gold plating machine #1", 10));

        mockMvc.perform(MockMvcRequestBuilders.get("/machine/plating1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("plating1"))
                .andExpect(jsonPath("description").value("Gold plating machine #1"))
                .andExpect(jsonPath("throughputMins").value(10));
    }

    @Test
    public void testGetMachineWithMachineNotFoundException() throws Exception {
        given(machineService.getMachine(anyString())).willThrow(new MachineNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("machine/plating9"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMachine() throws Exception {
        given(machineService.createMachine(any(Machine.class))).willReturn(any(Machine.class));

        mockMvc.perform(MockMvcRequestBuilders.post("machine")).andExpect(status().isCreated());
    }
}
