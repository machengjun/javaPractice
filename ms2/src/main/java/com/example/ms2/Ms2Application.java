package com.example.ms2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.ms2", "com.example.ms2.config"})
public class Ms2Application {

    public static void main(String[] args) {
        SpringApplication.run(Ms2Application.class, args);
    }

}
