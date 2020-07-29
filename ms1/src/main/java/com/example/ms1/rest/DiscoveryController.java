package com.example.ms1.rest;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 马成军
 **/
@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping(value = "/get")
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
//        List<String> list = new ArrayList<>();
//        list.get(1);
        return "Hello Nacos Discovery " + string;
    }

}
