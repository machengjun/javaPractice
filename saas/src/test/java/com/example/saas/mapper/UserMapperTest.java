package com.example.saas.mapper;

import com.example.saas.component.DsSchemaHolder;
import com.example.saas.dao.mapper.UserMapper;
import com.example.saas.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 马成军
 **/
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insertFriend() {
        DsSchemaHolder.setSchema("masterDs");
        User user = new User();
        user.setName("测试");
        user.setAge(33);
        userMapper.insert(user);
    }
}
