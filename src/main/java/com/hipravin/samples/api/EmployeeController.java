package com.hipravin.samples.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @GetMapping("/{impl}/bymail/{mail}")
    ResponseEntity<?> findEmployee(@PathVariable("impl") String impl, @PathVariable("mail") String mail) {
        return ResponseEntity.ok(new EmployeeDto(mail, mail, mail));
    }
}
