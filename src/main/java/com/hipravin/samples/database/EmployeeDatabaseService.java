package com.hipravin.samples.database;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("database")
public class EmployeeDatabaseService implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDatabaseService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeDatabaseService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Long count() {
        return employeeRepository.count();
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeRepository.findById(id).map(EmployeeDto::fromEntity);
    }

    @Override
    public List<EmployeeDto> findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString) {
        return employeeRepository.findByNameContains(containsString)
                .stream()
                .map(EmployeeDto::fromEntity)
                .collect(Collectors.toList());
    }
}
