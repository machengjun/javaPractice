package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void getMapper() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", "mcjt");
        queryWrapper.eq("age", 22);
        Map data = userService.getMap(queryWrapper);
        Assertions.assertNotNull(data);
    }

    /**
     * 多条件组合嵌套
     */
    @Test
    public void multiple() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getAge, User::getName).eq(User::getName, "mcjt").or(i -> i.eq(User::getAge, 22).isNotNull(User::getVersion));
        List<User> userList = userMapper.selectList(queryWrapper);
        Assertions.assertNotNull(userList);
    }

    /**
     * 自定义查询复用wrapper
     */
    @Test
    public void wrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getAge, User::getName).eq(User::getName, "mcjt").or(i -> i.eq(User::getAge, 22).isNotNull(User::getVersion));
        List<User> userList = userMapper.getSomeByWrapper(queryWrapper);
        Assertions.assertNotNull(userList);
    }

    @Test
    public void in() {
        List<String> ages = Arrays.asList("22", "1");
        List<User> userList = userMapper.getAgeIn(ages);
        Assertions.assertNotNull(userList);
    }

    @Test
    public void updataService() {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.lambda()
                .set(User::getName, "hahha")
                .setSql("age = age + 1")
                .eq(User::getAge, 22);
        boolean res = userService.update(userUpdateWrapper);
        Assertions.assertTrue(res);
    }

    @Test
    public void updataMapper() {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.lambda()
                .eq(User::getAge, 22);
        User user = new User();
        user.setAge(11);
        int res = userMapper.update(user, userUpdateWrapper);
        Assertions.assertNotNull(res);
    }

    @Test
    public void page() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().isNotNull(User::getAge).orderBy(true, true, User::getAge);
        Page<User> page = new Page<>(1, 1);
        IPage<User> data = userMapper.selectPage(page, userQueryWrapper);
        Assertions.assertNotNull(data);
    }

    @Test
    public void myPage() {
        Page page = new Page(1, 5);
        page.setTotal(100);
        long pageTotal = page.getPages();
        Assertions.assertNotNull(pageTotal);
    }


    /**
     * 链式写法
     */
    @Test
    public void chain() {
        List<User> data = userService.lambdaQuery().eq(User::getAge, 22)
                .orderBy(true, true, User::getAge).select(User::getName).list();
        Assertions.assertNotNull(data);
    }


}