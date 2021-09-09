package com.hipravin.samples.jsonb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeJsonbRepository extends JpaRepository<EmployeeJsonb, Long> {

//    @Query(value = "select * from EMPLOYEE_JSONB where CONTENT @> :email", nativeQuery = true)
    @Query(value = "select * from EMPLOYEE_JSONB where CONTENT @> jsonb_build_object('email', :email)", nativeQuery = true)
    List<EmployeeJsonb> findByEmail(String email);


    @Query(value = "select * from EMPLOYEE_JSONB where CONTENT->>'firstName' like %:contains% or CONTENT->>'lastName' like %:contains%", nativeQuery = true)
    List<EmployeeJsonb> findByFirstnameContainsOrLastNameContains(String contains);
}
