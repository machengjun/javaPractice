package com.example.auth2.rest;

import com.example.auth2.common.GenericResponse;
import com.example.auth2.common.ServiceError;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 权限测试
     */

    @GetMapping("/test")
    public GenericResponse test(){
        return GenericResponse.response(ServiceError.NORMAL,"test");
    }

    @PostMapping("/test")
    public GenericResponse testPost(){
        return GenericResponse.response(ServiceError.NORMAL,"testPOST");
    }

    @GetMapping("/test/a")
    public GenericResponse testA(){
        return GenericResponse.response(ServiceError.NORMAL,"testManage");
    }

    @GetMapping("/hello")
    public GenericResponse hello(){
        return GenericResponse.response(ServiceError.NORMAL,"hello security");
    }

    @GetMapping("/ddd")
    @PreAuthorize("hasAuthority('ddd:list')")
    public GenericResponse ddd(){
        return GenericResponse.response(ServiceError.NORMAL,"dddList");
    }

    @PostMapping("/ddd")
    @PreAuthorize("hasAuthority('ddd:add')")
    public GenericResponse dddd(){
        return GenericResponse.response(ServiceError.NORMAL,"testPOST");
    }
}
