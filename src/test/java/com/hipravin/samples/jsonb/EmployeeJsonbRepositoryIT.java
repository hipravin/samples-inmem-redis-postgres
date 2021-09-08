package com.hipravin.samples.jsonb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeJsonbRepositoryIT {
    @Autowired
    EmployeeJsonbRepository employeeJsonbRepository;

    @Test
    void testFindById() {
        Optional<EmployeeJsonb> ejb = employeeJsonbRepository.findById(1003001L);

        assertTrue(ejb.isPresent());
    }
}