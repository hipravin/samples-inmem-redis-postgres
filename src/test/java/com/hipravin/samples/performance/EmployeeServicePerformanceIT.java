package com.hipravin.samples.performance;

import com.hipravin.samples.util.RestPerformanceTester;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
public class EmployeeServicePerformanceIT {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    
    @Autowired
    RestPerformanceTester restPerformanceTester;

    static AtomicLong completedCount = new AtomicLong(0);

    @Test
    void perfFindById() throws InterruptedException {
        RestPerformanceTester.UpdatableStatistics updatableStatistics = restPerformanceTester.randomInfiniteGet(4,
                () -> "http://localhost:8080/api/v1/employee/database/byid/"
                        + ThreadLocalRandom.current().nextInt(1003000, 2003001));

        Thread.sleep(10_000);

        System.out.println("Stats: " + updatableStatistics.snapshot().toString());
    }

    @Test
    void perfFindByEmail() throws InterruptedException {
        RestPerformanceTester.UpdatableStatistics updatableStatistics = restPerformanceTester.randomInfiniteGet(4,
                () -> "http://localhost:8080/api/v1/employee/database/byemailexact/tyler.lewis@mail.com");

        Thread.sleep(10_000);

        System.out.println("Stats: " + updatableStatistics.snapshot().toString());
    }
    @Test
    void perfFindByContains() throws InterruptedException {
        RestPerformanceTester.UpdatableStatistics updatableStatistics = restPerformanceTester.randomInfiniteGet(4,
                () -> "http://localhost:8080/api/v1/employee/database/bynamecontains/lewis");

        Thread.sleep(10_000);

        System.out.println("Stats: " + updatableStatistics.snapshot().toString());
    }

    @Test
    void testSampleRestTemplate() throws InterruptedException {
        RestTemplate restTemplate = restTemplateBuilder.build();

        for (int i = 0; i < 4; i++) {
            infiniteSampeRt(restTemplate);
        }
        Thread.sleep(10_000);

        System.out.println("Total completed: " + completedCount);
    }

    public static void infiniteSampeRt(RestTemplate restTemplate) {
        CompletableFuture.runAsync(() -> {
            ResponseEntity<String> responseEntity =
                    restTemplate.getForEntity("http://localhost:8080/api/v1/employee/database/byid/1003001", String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                completedCount.incrementAndGet();
            }

            infiniteSampeRt(restTemplate);
        });
    }

    @Test
    void testSampleWebClient() throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080");


        for (int i = 0; i < 4; i++) {
            infiniteSampleWc(client);
        }

        Thread.sleep(10_000);
        System.out.println("Total completed: " + completedCount.get());
    }

    public static void infiniteSampleWc(WebClient client) {
        client.get().uri("/api/v1/employee/test")
                .retrieve().bodyToMono(String.class)
                .subscribe(s -> {
                    completedCount.incrementAndGet();
                    infiniteSampleWc(client);
                });
    }

    @Test
    void testFindById() throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080");


        for (int i = 0; i < 4; i++) {
            infiniteById(client);
        }

        Thread.sleep(10_000);
        System.out.println("Total completed: " + completedCount.get());
    }

    public static void infiniteById(WebClient client) {
        byid(client, "database", 1003001L)
                .subscribe(s -> {
            completedCount.incrementAndGet();
            infiniteById(client);
        });
    }

    public static Mono<String> byid(WebClient client, String impl, Long id) {
        return client.get().uri("/api/v1/employee/{impl}/byid/{id}", impl, id)
                .retrieve().bodyToMono(String.class);
    }
}
