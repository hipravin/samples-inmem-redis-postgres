package com.hipravin.samples.jsonb;

import com.hipravin.samples.api.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeJsonbServiceIT {
    @Autowired
    EmployeeJsonbService employeeJsonbService;

    @Test
    void testFindByEmail() {
        List<EmployeeDto> employeeDtoList = employeeJsonbService.findByEmail("ryan.dawson@mail.com");



        assertFalse(employeeDtoList.isEmpty());
    }
}