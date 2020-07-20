package com.example.mybatis.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA配置.
 */
@Configuration
@EnableJpaAuditing
@ComponentScan("com.example.mybatis.dao.jpa")
public class JpaConfig {
}
