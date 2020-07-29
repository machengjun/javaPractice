package com.example.ms2.service.feign;

import com.example.ms2.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 马成军
 **/
//@FeignClient(value = "ms1", configuration = FeignConfig.class, url = "192.168.188.196:21003")
@FeignClient(value = "ms1", configuration = FeignConfig.class, fallback = DiscoverServiceFallBack.class)
public interface DiscoverService {

    @GetMapping("/discovery/echo/{name}")
    String getEcho(@PathVariable String name);
}
