package com.example.demo.service;

import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SomeTest {
    @Autowired
    UserService userMapper;

    @Test
    public void test1() {
        Assertions.assertNotNull(userMapper);
    }

    @Test
    public void lock(){

    }
}
