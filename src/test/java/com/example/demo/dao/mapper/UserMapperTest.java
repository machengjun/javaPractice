package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Test
    public void delete() {
//        userService.removeById("9c833e2eeefc8959636050abeb5401e1");
        int res = userMapper.deleteById("7185081842e97254d0cc4bee5ae48cbd");
        Assertions.assertNotNull(res);
    }

    @Test
    public void find() {
        List<User> userList = userService.list(new QueryWrapper<>());
        Assertions.assertNotNull(userList);
    }

}