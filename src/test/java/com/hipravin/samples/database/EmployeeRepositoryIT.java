package com.hipravin.samples.database;

import com.hipravin.samples.DataGeneratorService;
import com.hipravin.samples.util.StreamBatchUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles({"local"})
class EmployeeRepositoryIT {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataGeneratorService dataGeneratorService;

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
}