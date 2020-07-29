package com.example.ms2.rest;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.example.ms2.service.feign.DiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @author 马成军
 **/
@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @Autowired
    @Qualifier("nacosRes")
    private RestTemplate restTemplate;

    @Autowired
    DiscoverService discoverService;

    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    public String ribbonEcho(@PathVariable String str) {
        return restTemplate.getForObject("http://ms1/discovery/echo/" + str, String.class);
    }


    @GetMapping(value = "/feignEcho/{str}")
    public String feignEcho(@PathVariable String str) throws InterruptedException {
        Thread.sleep(2000);
        return discoverService.getEcho("feignEcho");
    }
}


