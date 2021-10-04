package com.hipravin.samples.inmemory;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.api.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("inmemory")
public class EmployeeInMemoryService implements EmployeeService {
    final EmployeeInMemoryRepository employeeInMemoryRepository;

    public EmployeeInMemoryService(EmployeeInMemoryRepository employeeInMemoryRepository) {
        this.employeeInMemoryRepository = employeeInMemoryRepository;
    }

    @Override
    public Long count() {
        return employeeInMemoryRepository.count();
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeInMemoryRepository.findById(id)
                .map(EmployeeDto::fromEmployeeImmutable);
    }

    @Override
    public List<EmployeeDto> findByEmail(String email) {
        return employeeInMemoryRepository.findByEmailExact(email)
                .stream().map(EmployeeDto::fromEmployeeImmutable)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString) {
        return employeeInMemoryRepository.findByFirstNameOrLastNameContains(containsString)
                .stream().map(EmployeeDto::fromEmployeeImmutable)
                .collect(Collectors.toList());
    }
}
