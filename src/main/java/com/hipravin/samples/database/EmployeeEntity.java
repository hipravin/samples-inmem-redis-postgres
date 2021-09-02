package com.hipravin.samples.database;

import com.hipravin.samples.api.EmployeeDto;

import javax.persistence.*;

@Entity
@Table(name ="EMPLOYEE")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empSeq")
    @SequenceGenerator(sequenceName = "EMP_ID_SEQ", allocationSize = 1000, name = "empSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static EmployeeEntity fromDto(EmployeeDto employeeDto) {
        return new EmployeeEntity(employeeDto.getEmail(),
                employeeDto.getFirstName(),
                employeeDto.getLastName());
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
