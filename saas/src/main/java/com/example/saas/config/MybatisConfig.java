package com.example.saas.config;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author 马成军
 * @description mybatis分页插件
 */
@Configuration
@MapperScan("com.example.saas.dao.mapper")
public class MybatisConfig {

    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

    /**
     * SQL执行效率插件
     * 仅在 dev test 环境开启
     */
    @Bean
    @Profile({"dev", "test", "dev-cloud"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }



}