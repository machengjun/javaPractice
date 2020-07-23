package com.example.saas.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author 马成军
 **/
public class DsConfigBean {
    @Value("${javaPractice.datasource.driverClassName}")
    String driver;
    @Value("${javaPractice.datasource.url}")
    private String url;
    @Value("${javaPractice.datasource.username}")
    String username;
    @Value("${javaPractice.datasource.password}")
    String password;

    public DsConfigBean() {
    }

    public String getDriver() {
        return this.driver;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}