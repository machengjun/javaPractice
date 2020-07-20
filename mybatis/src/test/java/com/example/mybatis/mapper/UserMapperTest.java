package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis.dao.mapper.UserMapper;
import com.example.mybatis.entity.BaseEntity;
import com.example.mybatis.entity.User;
import com.example.mybatis.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @Test
    public void delete() {
        int res = userMapper.deleteById("0c4682d95827deb80c8e2f556de3191f");
        Assertions.assertNotNull(res);
    }

    /**
     * sql 注入器test
     */
    @Test
    public void deleteByIdWithFill() {
        User user = new User();
        user.setId("0c4682d95827deb80c8e2f556de3191f");
        user.setDeleteTime(LocalDateTime.now());
        int res = userMapper.deleteByIdWithFill(user);
        Assertions.assertNotNull(res);
    }

    /**
     * sql 注入器test
     */
    @Test
    public void update() {
        int res = userMapper.updateMcj("0c4682d95827deb80c8e2f556de3191f");
        Assertions.assertNotNull(res);
    }

    /**
     * 自定义mapper层 方法测试
     */
    @Test
    public void findOne() {
        User res = userMapper.findOne("0c4682d95827deb80c8e2f556de3191f");
        Assertions.assertNotNull(res);
    }

    @Test
    public void find() {
        List<User> userList = userService.list(new QueryWrapper<>());
        Assertions.assertNotNull(userList);
    }

    /**
     * 单个字段查询
     */
    @Test
    public void findOneColume() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getName).eq(BaseEntity::getId, "76dbf1ae45e1ded7a63bf52b960a5e28");
        List<Object> res = userMapper.selectObjs(queryWrapper);
        Assertions.assertNotNull(res);
    }

    /**
     * 单个字段查询
     */
    @Test
    public void findOneColume2() {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name").eq("id", "76dbf1ae45e1ded7a63bf52b960a5e28");
        List<String> res = userMapper.selectObjs(queryWrapper);
        Assertions.assertNotNull(res);
    }
}
