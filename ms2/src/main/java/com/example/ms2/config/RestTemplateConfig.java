package com.example.ms2.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 马成军
 **/
@Configuration
public class RestTemplateConfig {

    @LoadBalanced
    @Bean("nacosRes")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
