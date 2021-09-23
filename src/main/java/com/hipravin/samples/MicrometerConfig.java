package com.hipravin.samples;

import io.micrometer.core.instrument.binder.db.PostgreSQLDatabaseMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MicrometerConfig {
    @Bean
    PostgreSQLDatabaseMetrics dbMetrics(@Autowired DataSource dataSource) {
        return new PostgreSQLDatabaseMetrics(dataSource, "employees-database");
    }
}
