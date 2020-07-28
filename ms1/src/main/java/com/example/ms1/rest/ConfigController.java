package com.example.ms1.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author 马成军
 **/
@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigController {


    @Value(value = "${useLocalCache}")
    private boolean useLocalCache;

    @Value(value = "${mcj.name}")
    private String name;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

}
