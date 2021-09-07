package com.hipravin.samples.api;

import com.hipravin.samples.database.EmployeeEntity;
import com.hipravin.samples.inmemory.EmployeeImmutable;
import com.hipravin.samples.redis.EmployeeRedisHash;

public class EmployeeDto {
    Long id;
    String email;
    String firstName;
    String lastName;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public static EmployeeDto fromEmployeeImmutable(EmployeeImmutable employeeImmutable) {
        return new EmployeeDto(
                employeeImmutable.getId(),
                employeeImmutable.getEmail(),
                employeeImmutable.getFirstName(),
                employeeImmutable.getLastName());
    }

    public static EmployeeDto fromRedisHash(EmployeeRedisHash employeeRedisHash) {
        return new EmployeeDto(
                employeeRedisHash.getId(),
                employeeRedisHash.getEmail(),
                employeeRedisHash.getFirstName(),
                employeeRedisHash.getLastName());
    }

    public static EmployeeDto fromEntity(EmployeeEntity employeeEntity) {
        return new EmployeeDto(
                employeeEntity.getId(),
                employeeEntity.getEmail(),
                employeeEntity.getFirstName(),
                employeeEntity.getLastName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
