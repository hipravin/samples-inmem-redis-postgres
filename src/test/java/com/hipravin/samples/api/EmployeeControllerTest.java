package com.hipravin.samples.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSampleByMail() {
        ResponseEntity<String> sampleFind =
                restTemplate.getForEntity("http://localhost:" + port + "/api/v1/employee/inmem/bymail/test@mail.com", String.class);

        assertEquals(HttpStatus.OK, sampleFind.getStatusCode());
        assertNotNull(sampleFind.getBody());
        System.out.println(sampleFind.getBody());
    }
}