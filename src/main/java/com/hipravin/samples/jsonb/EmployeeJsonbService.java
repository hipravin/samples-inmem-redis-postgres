package com.hipravin.samples.jsonb;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.api.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeJsonbService implements EmployeeService {
//    final EmployeeJsonbRepository employeeJsonbRepository;
//
//    public EmployeeJsonbService(EmployeeJsonbRepository employeeJsonbRepository) {
//        this.employeeJsonbRepository = employeeJsonbRepository;
//    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<EmployeeDto> findByEmail(String email) {
        return null;
    }

    @Override
    public List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString) {
        return null;
    }
}
