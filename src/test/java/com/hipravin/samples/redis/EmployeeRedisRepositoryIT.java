package com.hipravin.samples.redis;

import com.hipravin.samples.DataGeneratorService;
import com.hipravin.samples.database.EmployeeRepository;
import com.hipravin.samples.util.StreamBatchUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeRedisRepositoryIT {
    @Autowired
    EmployeeRedisRepository employeeRedisRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataGeneratorService dataGeneratorService;

    @Autowired
    RedisTemplate<?,?> redisTemplate;

    @Test
    void testFindById() {
        Optional<EmployeeRedisHash> emps = employeeRedisRepository.findById(1003448L);
        assertTrue(emps.isPresent());
    }
    @Test
    void testFind() {
        List<EmployeeRedisHash> emps = employeeRedisRepository.findByEmail("jesse.mcdonald@mail.com");
        assertFalse(emps.isEmpty());

    }
    @Test
    void testFindContains() {
//        List<EmployeeRedisHash> emps = employeeRedisRepository.findByFirstNameContainingOrLastNameContaining("elson");
//        assertFalse(emps.isEmpty());
    }

    @Test
    void insert1M() {
        int count = 1_000_000;

        employeeRedisRepository.deleteAll();

        Stream<EmployeeRedisHash> randomEmployees = dataGeneratorService.randomEmployees()
                .map(EmployeeRedisHash::fromDto).limit(count);

//        employeeRedisRepository.saveAll((Iterable<? extends EmployeeRedisHash>) randomEmployees::iterator);


        StreamBatchUtil.batches(randomEmployees, 1000)
                .forEach(batch -> {
                    employeeRedisRepository.saveAll(batch);
                });

    }

    @Test
    @Transactional
    void postgreToRedis() {
        employeeRedisRepository.deleteAll();

        Stream<EmployeeRedisHash> employees = employeeRepository.getAllStream()
                .map(EmployeeRedisHash::fromEntity);

        StreamBatchUtil.batches(employees, 1000)
                .forEach(batch -> {
                    employeeRedisRepository.saveAll(batch);
                });

    }

    @Test
    void testInsertSome() {
        employeeRedisRepository.deleteAll();
        int count = 1000;

        Stream<EmployeeRedisHash> randomEmployees = dataGeneratorService.randomEmployees()
                .map(EmployeeRedisHash::fromDto).limit(count);

        List<EmployeeRedisHash> entities = randomEmployees.collect(Collectors.toList());

        employeeRedisRepository.saveAll(entities);
    }

    @Test
    void deleteAll() {
        employeeRedisRepository.deleteAll();
    }

    @Test
    void testCount() {
        System.out.println("Total: " + employeeRedisRepository.count());
    }
}