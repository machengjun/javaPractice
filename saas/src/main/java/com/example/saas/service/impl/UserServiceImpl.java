package com.example.saas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.saas.annotation.TenantDS;
import com.example.saas.dao.mapper.UserMapper;
import com.example.saas.entity.User;
import com.example.saas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @TenantDS
    @Override
    public User getMyFriend() {
        User user = userMapper.selectOne(new QueryWrapper<>());
        return user;
    }

    @TenantDS
    @Override
    public void insertMyFriend(User user) {
        userMapper.insert(user);
    }
}
