package com.hipravin.samples.init;

import com.hipravin.samples.DataGeneratorService;
import com.hipravin.samples.database.EmployeeEntity;
import com.hipravin.samples.database.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Profile("runners")
@Order(InitRunners.ORDER_DB)
public class DbInitRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DbInitRunner.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Override
    public void run(ApplicationArguments args) {
        if(employeeRepository.count() == 0) {
            log.info("Fill db with sample data");

            Stream<EmployeeEntity> randomEmployees = dataGeneratorService.randomEmployees()
                    .map(EmployeeEntity::fromDto).limit(1000);

            List<EmployeeEntity> entities = randomEmployees.collect(Collectors.toList());

            employeeRepository.saveAll(entities);
        } else {
            log.info("Db not empty - skip fill");
        }
    }
}
