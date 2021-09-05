package com.hipravin.samples.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Service
public class RestPerformanceTester {
    private final RestTemplateBuilder restTemplateBuilder;
    private final RestTemplate restTemplate;
    private volatile boolean suspended = false;

    public RestPerformanceTester(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplateBuilder.build();
    }

    public UpdatableStatistics randomInfiniteGet(int nthreads, Supplier<String> randomGetUrlSupplier) {
        UpdatableStatistics statistics = new UpdatableStatistics();

        ExecutorService executorService = Executors.newFixedThreadPool(nthreads);

        for (int i = 0; i < nthreads; i++) {
            executorService.submit(() -> {
                while (!suspended) {
                    singleGet(restTemplate, statistics, randomGetUrlSupplier.get());
                }
            });
        }
        executorService.shutdown();

        return statistics;
    }

    private static String random(List<String> pool) {
        return pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
    }

    private static void singleGet(RestTemplate restTemplate, UpdatableStatistics updatableStatistics, String getUrl) {

        Instant start = Instant.now();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl, String.class);

        long responseMs = Duration.between(start, Instant.now()).toMillis();
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            updatableStatistics.addCompleted(responseMs);
        }
    }

    public static class UpdatableStatistics {
        private final AtomicReference<Long> maxResponseMs = new AtomicReference<>(0L);
        private final AtomicLong totalCompletedResponseMs = new AtomicLong(0L);
        private final AtomicLong totalCompleted = new AtomicLong(0);
        private final AtomicLong totalFailed = new AtomicLong(0);

        public void addCompleted(long responseMs) {
            //consider spin loop
            long mms = maxResponseMs.get();
            if(responseMs > mms) {
                maxResponseMs.set(responseMs);
            }
            totalCompleted.incrementAndGet();
            totalCompletedResponseMs.addAndGet(responseMs);
        }
        public void addFailed(long responseMs) {
            totalFailed.incrementAndGet();
        }

        public StatisticsSnapshot snapshot() {
            return new StatisticsSnapshot(maxResponseMs.get(),
                    totalCompletedResponseMs.get() / (totalCompleted.get()),
                    totalCompleted.get(), totalFailed.get());
        }
    }

    public static class StatisticsSnapshot {
        private final long maxResponseMs;
        private final long averageresponseMs;
        private final long totalCompleted;
        private final long failed;

        public StatisticsSnapshot(long maxResponseMs, long averageresponseMs, long totalCompleted, long failed) {
            this.maxResponseMs = maxResponseMs;
            this.averageresponseMs = averageresponseMs;
            this.totalCompleted = totalCompleted;
            this.failed = failed;
        }

        @Override
        public String toString() {
            return "StatisticsSnapshot{" +
                    "maxResponseMs=" + maxResponseMs +
                    ", averageresponseMs=" + averageresponseMs +
                    ", totalCompleted=" + totalCompleted +
                    ", failed=" + failed +
                    '}';
        }
    }

    @PreDestroy
    void preDestroy() {
        System.out.println("PreDestroy called");
        suspended = true;
    }

}
