package com.hipravin.samples.api;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDto> findById(Long id);
    List<EmployeeDto> findByEmail(String email);
    List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString);
}
