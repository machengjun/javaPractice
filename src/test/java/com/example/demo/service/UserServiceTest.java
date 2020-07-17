package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.openmbean.CompositeDataSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() {
        Assertions.assertNotNull(userService);
    }

    /**
     * getMap
     */
    @Test
    public void getMapper(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name","mcjt");
        queryWrapper.eq("age",22);
        Map data = userService.getMap(queryWrapper);
        Assertions.assertNotNull(data);
    }

    /**
     * 多条件组合嵌套
     */
    @Test
    public void multiple(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getAge,User::getName).eq(User::getName,"mcjt").or(i->i.eq(User::getAge,22).isNotNull(User::getVersion));
        List<User> userList = userMapper.selectList(queryWrapper);
        Assertions.assertNotNull(userList);
    }

    /**
     * 自定义查询复用wrapper
     */
    @Test
    public void wrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getAge,User::getName).eq(User::getName,"mcjt").or(i->i.eq(User::getAge,22).isNotNull(User::getVersion));
        List<User> userList = userMapper.getSomeByWrapper(queryWrapper);
        Assertions.assertNotNull(userList);
    }

    @Test
    public void in(){
        List<String> ages = Arrays.asList("22","1");
        List<User> userList = userMapper.getAgeIn(ages);
        Assertions.assertNotNull(userList);
    }


}