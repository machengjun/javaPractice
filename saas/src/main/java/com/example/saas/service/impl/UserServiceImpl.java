package com.example.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.saas.annotation.TenantDS;
import com.example.saas.dao.mapper.UserMapper;
import com.example.saas.entity.User;
import com.example.saas.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @TenantDS
    @Override
    public User getMyFriend() {
        log.info("getMyFriend coming");
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @TenantDS
    @Override
    public void insertMyFriend(User user) {
        userMapper.insert(user);
    }
}
