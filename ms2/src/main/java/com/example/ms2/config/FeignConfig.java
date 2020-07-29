package com.example.ms2.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Autowired
    private Jackson2ObjectMapperBuilder builder;

    public FeignConfig() {
    }

    public void apply(RequestTemplate requestTemplate) {
        this.builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization");
            if (token != null) {
                requestTemplate.header("Authorization", new String[]{token});
                if (request.getHeaders("schema").hasMoreElements()) {
                    String schema = request.getHeaders("schema").nextElement();
                    requestTemplate.header("schema", new String[]{schema});
                }
            }
        }

    }


}
