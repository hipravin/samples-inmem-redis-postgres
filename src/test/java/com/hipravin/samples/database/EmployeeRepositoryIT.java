package com.hipravin.samples.database;

import com.hipravin.samples.DataGeneratorService;
import com.hipravin.samples.util.StreamBatchUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles({"local"})
@Transactional
class EmployeeRepositoryIT {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataGeneratorService dataGeneratorService;

    @Test
    void testFindByEmail() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findByEmail("tyler.lewis@mail.com");

        assertFalse(employeeEntities.isEmpty());
    }

    @Test
    void testFindByContains() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findByNameContains("yle");

        assertFalse(employeeEntities.isEmpty());
    }

    @Test
    void insert1M() {
        int count = 1_000_000;

        Stream<EmployeeEntity> randomEmployees = dataGeneratorService.randomEmployees()
                .map(EmployeeEntity::fromDto).limit(count);

        StreamBatchUtil.batches(randomEmployees, 1000)
                .forEach(batch -> {
                    employeeRepository.saveAll(batch);
                });

    }

    @Test
    void testInsertSome() {
        int count = 1000;

        Stream<EmployeeEntity> randomEmployees = dataGeneratorService.randomEmployees()
                .map(EmployeeEntity::fromDto).limit(count);

        List<EmployeeEntity> entities = randomEmployees.collect(Collectors.toList());

        employeeRepository.saveAll(entities);
    }

    @Test
    void readAllTest() {
        Instant start = Instant.now();
        System.out.println("Employees: " + employeeRepository.getAllStream().count());
        System.out.println("Loading took:" + Duration.between(start, Instant.now()));
    }
}