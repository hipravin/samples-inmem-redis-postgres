package com.hipravin.samples.init;

import com.hipravin.samples.DataGeneratorService;
import com.hipravin.samples.database.EmployeeRepository;
import com.hipravin.samples.inmemory.EmployeeInMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("runners")
@Order(InitRunners.ORDER_INMEM)
public class InMemoryInitializerRunner implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InMemoryInitializerRunner.class);

    final EmployeeRepository employeeRepository;
    final EmployeeInMemoryRepository employeeInMemoryRepository;

    public InMemoryInitializerRunner(EmployeeRepository employeeRepository,
                                     EmployeeInMemoryRepository employeeInMemoryRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeInMemoryRepository = employeeInMemoryRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.info("Fill inmemory storage - started...");

        employeeInMemoryRepository.fillFromDatabase(employeeRepository.getAllStream());

        log.info("Fill inmemory storage - finished");

    }
}
