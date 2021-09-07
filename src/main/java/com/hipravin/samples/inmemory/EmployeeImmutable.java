package com.hipravin.samples.inmemory;

import com.hipravin.samples.database.EmployeeEntity;

public class EmployeeImmutable {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;

    public EmployeeImmutable(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static EmployeeImmutable fromEntity(EmployeeEntity employeeEntity) {
        return new EmployeeImmutable(
                employeeEntity.getId(),
                employeeEntity.getEmail(),
                employeeEntity.getFirstName(),
                employeeEntity.getLastName());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
