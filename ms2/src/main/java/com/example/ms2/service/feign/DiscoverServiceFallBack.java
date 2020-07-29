package com.example.ms2.service.feign;

import org.springframework.stereotype.Service;

/**
 * @author 马成军
 **/
@Service
public class DiscoverServiceFallBack implements DiscoverService {
    @Override
    public String getEcho(String name) {
        return "服务降级";
    }
}
