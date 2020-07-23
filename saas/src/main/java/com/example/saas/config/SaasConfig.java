package com.example.saas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 马成军
 **/
@Configuration
@ComponentScan
@ConditionalOnExpression("${javaPractice.saas.enable:true}")
public class SaasConfig {
    private static final Logger log = LoggerFactory.getLogger(SaasConfig.class);

    public SaasConfig() {
        log.debug("SaasConfig...");
    }
}