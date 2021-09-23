package com.hipravin.samples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataGeneratorServiceTest {

    @Autowired
    DataGeneratorService dataGeneratorService;

    @Test
    void testGenerateRandomEmployees() {
        dataGeneratorService.randomEmployees().limit(100)
                .forEach(System.out::println);
    }
}