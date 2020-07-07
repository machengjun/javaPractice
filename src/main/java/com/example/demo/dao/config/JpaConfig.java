package com.example.demo.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA配置.
 */
@Configuration
@EnableJpaAuditing
@ComponentScan("com.example.demo.dao.jpa")
public class JpaConfig {
}
