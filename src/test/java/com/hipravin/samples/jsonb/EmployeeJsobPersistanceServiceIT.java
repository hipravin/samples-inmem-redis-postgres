package com.hipravin.samples.jsonb;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.database.EmployeeEntity;
import com.hipravin.samples.database.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootTest
@Transactional
class EmployeeJsobPersistanceServiceIT {

    @Autowired
    EmployeeJsobPersistanceService employeeJsobPersistanceService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void insertSome() {
        Stream<EmployeeEntity> employeeEntityStream = employeeRepository.getAllStream().skip(1).limit(10);
        Stream<EmployeeDto> dtos = employeeEntityStream.map(EmployeeDto::fromEntity).peek(e -> System.out.println(e.getEmail()));

        employeeJsobPersistanceService.saveAll(dtos);
    }

}