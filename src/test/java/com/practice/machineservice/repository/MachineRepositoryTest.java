package com.practice.machineservice.repository;

import com.practice.machineservice.model.Machine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MachineRepositoryTest {

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testGetMachine_returnMachineDetails() {

        Machine result = machineRepository.findByName("plating1");
        assertThat(result.getName()).isEqualTo("plating1");
    }


}