package com.practice.machineservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.machineservice.exception.MachineNotFoundException;
import com.practice.machineservice.model.Machine;
import com.practice.machineservice.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static com.practice.machineservice.utils.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class MachineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MachineService machineService;

    @Test
    public void testGetAllMachines() throws Exception {
        given(machineService.getAllMachine()).willReturn(Arrays.asList(MACHINE, UPDATED_MACHINE));

        mockMvc.perform(MockMvcRequestBuilders.get("/machines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("machines").isArray())
                .andExpect(jsonPath("machines", hasSize(2)))
                .andExpect(jsonPath("machines[0].id").value(MACHINE.getId()))
                .andExpect(jsonPath("machines[0].name").value(MACHINE.getName()))
                .andExpect(jsonPath("machines[0].description").value(MACHINE.getDescription()))
                .andExpect(jsonPath("machines[0].throughputMins").value(MACHINE.getThroughputMins()))
                .andExpect(jsonPath("machines[1].id").value(UPDATED_MACHINE.getId()))
                .andExpect(jsonPath("machines[1].name").value(UPDATED_MACHINE.getName()))
                .andExpect(jsonPath("machines[1].description").value(UPDATED_MACHINE.getDescription()))
                .andExpect(jsonPath("machines[1].throughputMins").value(UPDATED_MACHINE.getThroughputMins()));
    }


    @Test
    public void testGetMachineByName() throws Exception {
        given(machineService.getMachine("plating1")).willReturn(Collections.singletonList(MACHINE));

        mockMvc.perform(MockMvcRequestBuilders.get("/machines/plating1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("machines").isArray())
                .andExpect(jsonPath("machines", hasSize(1)))
                .andExpect(jsonPath("machines[0].id").isNumber())
                .andExpect(jsonPath("machines[0].name").value(MACHINE.getName()))
                .andExpect(jsonPath("machines[0].description").value(MACHINE.getDescription()))
                .andExpect(jsonPath("machines[0].throughputMins").value(MACHINE.getThroughputMins()));
    }

    @Test
    public void testGetMachineById() throws Exception {
        given(machineService.getMachineById(ORIGINAL_ID)).willReturn(MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.get("/machine/" + ORIGINAL_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(MACHINE.getId()))
                .andExpect(jsonPath("name").value(MACHINE.getName()))
                .andExpect(jsonPath("description").value(MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(MACHINE.getThroughputMins()));
    }

    @Test
    public void testGetMachineByIdNotFound() throws Exception {
        given(machineService.getMachineById(anyLong())).willThrow(new MachineNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/machine/" + ORIGINAL_ID))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetMachineByNameWhenMachineNotFound() throws Exception {
        given(machineService.getMachine(anyString())).willThrow(new MachineNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/machines/plating9"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMachine() throws Exception {
        given(machineService.createMachine(any(Machine.class))).willReturn(MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.post("/machine/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MACHINE)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value(MACHINE.getName()))
                .andExpect(jsonPath("description").value(MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(MACHINE.getThroughputMins()));
    }

    @Test
    public void testCreateMachineWithInvalidName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/machine/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_NAME_MACHINE_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("name").value("name must be less than/equal to 40 characters"))
                .andExpect(jsonPath("timestamp").exists());
    }

    @Test
    public void testUpdateMachineDescription() throws Exception {
        given(machineService.updateMachine(eq(UPDATE_ID), any(Machine.class))).willReturn(UPDATE_DESC_MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.post("/machine/" + UPDATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_MACHINE_DESC_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(UPDATE_DESC_MACHINE.getId()))
                .andExpect(jsonPath("name").value(UPDATE_DESC_MACHINE.getName()))
                .andExpect(jsonPath("description").value(UPDATE_DESC_MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(UPDATE_DESC_MACHINE.getThroughputMins()));
    }

    @Test
    public void testCreateMachineWithInvalidThroughput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/machine/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_THROUGHPUT_MACHINE_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("throughputMins").value("throughput per minute must be a positive integer"))
                .andExpect(jsonPath("timestamp").exists());
    }

    /**
     * Extra use cases on top of the minimal requirements
     */
    @Test
    public void testUpdateMachineName() throws Exception {
        given(machineService.updateMachine(eq(UPDATE_ID), any(Machine.class))).willReturn(UPDATE_NAME_MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.post("/machine/" + UPDATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_MACHINE_NAME_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(UPDATE_NAME_MACHINE.getId()))
                .andExpect(jsonPath("name").value(UPDATE_NAME_MACHINE.getName()))
                .andExpect(jsonPath("description").value(UPDATE_NAME_MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(UPDATE_NAME_MACHINE.getThroughputMins()));
    }

    @Test
    public void testUpdateMachineThroughput() throws Exception {
        given(machineService.updateMachine(eq(UPDATE_ID), any(Machine.class))).willReturn(UPDATE_THROUGHPUT_MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.post("/machine/" + UPDATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATE_MACHINE_THROUGHPUT_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(UPDATE_THROUGHPUT_MACHINE.getId()))
                .andExpect(jsonPath("name").value(UPDATE_THROUGHPUT_MACHINE.getName()))
                .andExpect(jsonPath("description").value(UPDATE_THROUGHPUT_MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(UPDATE_THROUGHPUT_MACHINE.getThroughputMins()));
    }

    @Test
    public void testReplaceMachineWhenIdIsNew() throws Exception {
        given(machineService.getMachineById(UPDATE_ID)).willReturn(null);
        given(machineService.createMachine(any(Machine.class))).willReturn(UPDATE_DESC_MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.put("/machine/" + UPDATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UPDATE_DESC_MACHINE)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(UPDATE_DESC_MACHINE.getId()))
                .andExpect(jsonPath("name").value(UPDATE_DESC_MACHINE.getName()))
                .andExpect(jsonPath("description").value(UPDATE_DESC_MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(UPDATE_DESC_MACHINE.getThroughputMins()));
    }

    @Test
    public void testReplaceMachineWhenIdExists() throws Exception {
        given(machineService.getMachineById(UPDATE_ID)).willReturn(UPDATE_DESC_MACHINE);
        given(machineService.updateMachine(anyLong(), any(Machine.class))).willReturn(UPDATE_DESC_MACHINE);

        mockMvc.perform(MockMvcRequestBuilders.put("/machine/" + UPDATE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UPDATE_DESC_MACHINE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(UPDATE_DESC_MACHINE.getId()))
                .andExpect(jsonPath("name").value(UPDATE_DESC_MACHINE.getName()))
                .andExpect(jsonPath("description").value(UPDATE_DESC_MACHINE.getDescription()))
                .andExpect(jsonPath("throughputMins").value(UPDATE_DESC_MACHINE.getThroughputMins()));
    }


}
