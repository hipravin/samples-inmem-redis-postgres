package com.hipravin.samples.jsonb;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.database.EmployeeEntity;
import com.hipravin.samples.database.EmployeeRepository;
import com.hipravin.samples.redis.EmployeeRedisHash;
import com.hipravin.samples.util.StreamBatchUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
class EmployeeJsobPersistanceServiceIT {

    @Autowired
    EmployeeJsobPersistanceService employeeJsobPersistanceService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void insertAll() {
        Stream<EmployeeDto> employees = employeeRepository.getAllStream()
                .map(EmployeeDto::fromEntity);

        AtomicLong counter = new AtomicLong(0);

        StreamBatchUtil.batches(employees, 1000)
                .peek(b -> System.out.println("Batch #" + counter.incrementAndGet()))
                .forEach(batch -> {
                    CompletableFuture.runAsync(() -> {
                        employeeJsobPersistanceService.saveAll(batch.stream());
                    }).join();
                });
    }

    @Test
    void insertSome() {
        Stream<EmployeeEntity> employeeEntityStream = employeeRepository.getAllStream().limit(10);
        Stream<EmployeeDto> dtos = employeeEntityStream.map(EmployeeDto::fromEntity).peek(e -> System.out.println(e.getEmail()));

        CompletableFuture.runAsync(() -> {
            employeeJsobPersistanceService.saveAll(dtos);
        }).join();
    }
}