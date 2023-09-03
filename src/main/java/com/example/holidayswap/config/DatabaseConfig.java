package com.example.holidayswap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class DatabaseConfig {
    /**
     * @return a simple username as auditor
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return "Administrator"::describeConstable;
    }
}
