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
    public void testFindByName() {
        Machine savedEntity = entityManager.persistFlushFind(new Machine("plating1", "Gold plating machine #1", 10));
        Machine actualEntity = machineRepository.findByName("plating1");
        assertThat(actualEntity.getName()).isEqualTo(savedEntity.getName());
        assertThat(actualEntity.getDescription()).isEqualTo(savedEntity.getDescription());
        assertThat(actualEntity.getThroughputMins()).isEqualTo(savedEntity.getThroughputMins());
    }


}