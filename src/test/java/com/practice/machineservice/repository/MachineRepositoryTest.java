package com.practice.machineservice.repository;

import com.practice.machineservice.model.Machine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static com.practice.machineservice.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MachineRepositoryTest {

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testGetById() {
        Machine savedEntity = entityManager.persistFlushFind(UNSAVED_MACHINE);
        Optional<Machine> actual = machineRepository.getById(savedEntity.getId());

        assertThat(actual.isPresent()).isTrue();

        Machine actualEntity = actual.get();

        assertThat(actualEntity.getId()).isEqualTo(savedEntity.getId());
        assertThat(actualEntity.getName()).isEqualTo(savedEntity.getName());
        assertThat(actualEntity.getDescription()).isEqualTo(savedEntity.getDescription());
        assertThat(actualEntity.getThroughputMins()).isEqualTo(savedEntity.getThroughputMins());
    }

    @Test
    public void testGetByIdNotFound() {
        Optional<Machine> result = machineRepository.getById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindAll() {
        Machine savedEntity = entityManager.persistFlushFind(UNSAVED_MACHINE_3);
        List<Machine> results = machineRepository.findAll();

        assertThat(results.size()).isEqualTo(1);
        Machine actualEntity = results.get(0);
        assertThat(actualEntity.getId()).isEqualTo(savedEntity.getId());
        assertThat(actualEntity.getName()).isEqualTo(savedEntity.getName());
        assertThat(actualEntity.getDescription()).isEqualTo(savedEntity.getDescription());
        assertThat(actualEntity.getThroughputMins()).isEqualTo(savedEntity.getThroughputMins());
    }

    @Test
    public void testFindAllWithEmptyResult() {
        List<Machine> result = machineRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindAllByNameNotFound() {
        List<Machine> results = machineRepository.findAllByName("plating1");
        assertThat(results).isNullOrEmpty();
    }


    @Test
    public void testFindAllByName() {
        Machine savedEntity = entityManager.persistFlushFind(UNSAVED_MACHINE_2);
        List<Machine> results = machineRepository.findAllByName(savedEntity.getName());

        assertThat(results.size()).isEqualTo(1);
        Machine actualEntity = results.get(0);
        assertThat(actualEntity.getId()).isEqualTo(savedEntity.getId());
        assertThat(actualEntity.getName()).isEqualTo(savedEntity.getName());
        assertThat(actualEntity.getDescription()).isEqualTo(savedEntity.getDescription());
        assertThat(actualEntity.getThroughputMins()).isEqualTo(savedEntity.getThroughputMins());
    }
}