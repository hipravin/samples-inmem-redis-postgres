package com.hipravin.samples.redis;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRedisRepository extends CrudRepository<EmployeeRedisHash, String> {
}
