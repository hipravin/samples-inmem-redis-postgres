package com.hipravin.samples.jsonb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.api.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("jsonb")
public class EmployeeJsonbService implements EmployeeService {
    final EmployeeJsonbRepository employeeJsonbRepository;
    final ObjectMapper objectMapper;

    public EmployeeJsonbService(EmployeeJsonbRepository employeeJsonbRepository, ObjectMapper objectMapper) {
        this.employeeJsonbRepository = employeeJsonbRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeJsonbRepository.findById(id)
                .map(e -> fromJsonQuiet(e.getContent()));
    }

    @Override
    public List<EmployeeDto> findByEmail(String email) {
        return employeeJsonbRepository.findByEmail(email)
                .stream().map(e -> fromJsonQuiet(e.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString) {
        return employeeJsonbRepository.findByFirstnameContainsOrLastNameContains(containsString)
                .stream().map(e -> fromJsonQuiet(e.getContent()))
                .collect(Collectors.toList());
    }

    private EmployeeDto fromJsonQuiet(String json) {
        try {
            return objectMapper.readValue(json, EmployeeDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
