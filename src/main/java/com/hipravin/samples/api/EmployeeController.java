package com.hipravin.samples.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeDatabaseService;

    public EmployeeController(@Qualifier("database") EmployeeService employeeDatabaseService) {
        this.employeeDatabaseService = employeeDatabaseService;
    }

    @GetMapping("/{impl}/byid/{id}")
    ResponseEntity<?> findById(@PathVariable("impl") String impl,
                               @PathVariable("id") Long id) {
        EmployeeService employeeService = chooseImpl(impl);
        Optional<EmployeeDto> employee = employeeService.findById(id);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return new ResponseEntity<Object>("Employee not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{impl}/byemailexact/{email}")
    ResponseEntity<?> findByEmailExact(@PathVariable("impl") String impl,
                                       @PathVariable("email") String email) {
        EmployeeService employeeService = chooseImpl(impl);
        List<EmployeeDto> employees = employeeService.findByEmail(email);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{impl}/bynamecontains/{contains}")
    ResponseEntity<?> findByFirstNameOrLastNameContains(@PathVariable("impl") String impl,
                                                        @PathVariable("contains") String contains) {
        EmployeeService employeeService = chooseImpl(impl);
        List<EmployeeDto> employees = employeeService.findByFirstNameOrLastNameContains(contains);
        return ResponseEntity.ok(employees);
    }

    private EmployeeService chooseImpl(String impl) {
        String implLowerCase = impl.toLowerCase();
        switch (implLowerCase) {
            case "database":
                return employeeDatabaseService;
            case "redis":
                return employeeDatabaseService;
            case "inmemory":
                return employeeDatabaseService;
            default:
                throw new IllegalArgumentException("No employeeService found matching: " + impl);
        }
    }
}