package com.hipravin.samples.jsonb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hipravin.samples.api.EmployeeDto;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@Repository
public class EmployeeJsobPersistanceService {
    final EmployeeJsonbRepository employeeJsonbRepository;
    final ObjectMapper objectMapper;

    public EmployeeJsobPersistanceService(EmployeeJsonbRepository employeeJsonbRepository, ObjectMapper objectMapper) {
        this.employeeJsonbRepository = employeeJsonbRepository;
        this.objectMapper = objectMapper;
    }

    public void saveAll(Stream<EmployeeDto> employeeDtoStream) {
        Stream<EmployeeJsonb> employeeJsonbStream = employeeDtoStream
                .map(e -> new EmployeeJsonb(e.getId(), serializeToJson(e).getBytes(StandardCharsets.UTF_8)));

        employeeJsonbRepository.saveAll((Iterable<? extends EmployeeJsonb>) employeeJsonbStream::iterator) ;
    }

    String serializeToJson(EmployeeDto employeeDto) {
        try {
            return objectMapper.writeValueAsString(employeeDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
