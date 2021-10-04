package com.hipravin.samples.inmemory;

import com.hipravin.samples.database.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeInMemoryRepository {
    private static final Logger log = LoggerFactory.getLogger(EmployeeInMemoryRepository.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private final Map<Long, EmployeeImmutable> idToEmployee = new HashMap<>();

    public void fillFromDatabase(Stream<EmployeeEntity> employeeEntityStream) {
        updateWithWriteLock(() -> {
            clear();

            employeeEntityStream.forEach(ee -> {
                EmployeeImmutable ei = EmployeeImmutable.fromEntity(ee);
                idToEmployee.put(ei.getId(), ei);
            });

            return null;
        });
    }

    public long count() {
        return getWithReadLock(() -> idToEmployee.size());
    }

    public Optional<EmployeeImmutable> findById(Long id) {
        return getWithReadLock(() -> Optional.ofNullable(idToEmployee.get(id)));

    }

    public List<EmployeeImmutable> findByEmailExact(String email) {
        Objects.requireNonNull(email);
        return getWithReadLock(() -> idToEmployee.values().stream()
                .filter(e -> email.equals(e.getEmail()))
                .collect(Collectors.toList()));
    }

    public List<EmployeeImmutable> findByFirstNameOrLastNameContains(String contains) {
        Objects.requireNonNull(contains);
        return getWithReadLock(() -> idToEmployee.values().stream()
                .filter(e -> e.getFirstName() != null && e.getFirstName().contains(contains)
                        || e.getLastName() != null && e.getLastName().contains(contains))
                .collect(Collectors.toList()));
    }

    void clear() {
        idToEmployee.clear();
    }

    <T> T getWithReadLock(Supplier<? extends T> getFunction) {
        readLock.lock();
        try {
            return getFunction.get();
        } finally {
            readLock.unlock();
        }
    }

    void updateWithWriteLock(Supplier<Void> updateFunction) {
        writeLock.lock();
        try {
            updateFunction.get();
        } finally {
            writeLock.unlock();
        }
    }
}
