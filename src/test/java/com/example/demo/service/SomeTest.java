package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class SomeTest {
    @Autowired
    UserService userMapper;

    @Test
    public void test1() {
        Assertions.assertNotNull(userMapper);
    }

    @Test
    public void lock(){
        log.info("test lock coming");
    }
}
