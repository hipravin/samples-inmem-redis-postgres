package com.hipravin.samples.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRedisRepository extends CrudRepository<EmployeeRedisHash, Long> {
    List<EmployeeRedisHash> findByEmail(String email);

    //containing is not supported by spring data redis
//    List<EmployeeRedisHash> findByFirstNameContainingOrLastNameContaining(String contains);
}
