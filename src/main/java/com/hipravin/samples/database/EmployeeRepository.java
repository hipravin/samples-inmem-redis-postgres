package com.hipravin.samples.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
//https://www.geekyhacker.com/2019/03/26/high-performance-data-fetching-using-spring-data-jpa-stream/
// no performance gain from the hints in my tests

//    @QueryHints(value = {
//            @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE),
//            @QueryHint(name = HINT_CACHEABLE, value = "false"),
//            @QueryHint(name = READ_ONLY, value = "true")
//    })

    @Query("select e from EmployeeEntity e")
    Stream<EmployeeEntity> getAllStream();

    List<EmployeeEntity> findByEmail(String email);

    @Query("select e from EmployeeEntity e where e.firstName like %:contains% or e.lastName like %:contains%")
    List<EmployeeEntity> findByNameContains(String contains);
}
