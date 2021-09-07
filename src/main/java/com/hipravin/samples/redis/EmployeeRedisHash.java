package com.hipravin.samples.redis;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.database.EmployeeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("Employee")
public class EmployeeRedisHash {
    @Id
    private Long id;

    @Indexed
    private String email;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;

    public EmployeeRedisHash() {
    }

    public EmployeeRedisHash(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static EmployeeRedisHash fromDto(EmployeeDto employeeDto) {
        return new EmployeeRedisHash(
                employeeDto.getId(),
                employeeDto.getEmail(),
                employeeDto.getFirstName(),
                employeeDto.getLastName());
    }

    public static EmployeeRedisHash fromEntity(EmployeeEntity employeeEntity) {
        return new EmployeeRedisHash(
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

}
