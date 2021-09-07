package com.hipravin.samples.redis;

import com.hipravin.samples.api.EmployeeDto;
import com.hipravin.samples.api.EmployeeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("redis")
public class EmployeeRedisService implements EmployeeService {
    final EmployeeRedisRepository employeeRedisRepository;
    final RedisTemplate<?,?> redisTemplate;

    public EmployeeRedisService(EmployeeRedisRepository employeeRedisRepository, RedisTemplate<?, ?> redisTemplate) {
        this.employeeRedisRepository = employeeRedisRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<EmployeeDto> findById(Long id) {
        return employeeRedisRepository.findById(id).map(EmployeeDto::fromRedisHash);
    }

    @Override
    public List<EmployeeDto> findByEmail(String email) {
        return employeeRedisRepository.findByEmail(email)
                .stream().map(EmployeeDto::fromRedisHash)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findByFirstNameOrLastNameContains(String containsString) {
        return null;
    }
}
