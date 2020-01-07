package com.practice.machineservice;


import com.practice.machineservice.model.Machine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MachineServiceIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testGetMachine() {
        ResponseEntity<Machine> resp = restTemplate.exchange("/machine/plating1", HttpMethod.GET, null, Machine.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().getName()).isEqualTo("plating1");
        assertThat(resp.getBody().getDescription()).isEqualTo("Gold plating machine #1");
        assertThat(resp.getBody().getThroughputMins()).isEqualTo(10);
    }

}
