package com.example.auth.service;


import com.example.auth.dao.mapper.RoleMapper;
import com.example.auth.dao.mapper.UserMapper;
import com.example.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Piconjo
 * @date 2020/5/19  16:25
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int addUser(User user) {
        roleMapper.addRole(user.getUsername(),"USER");
        return userMapper.addUser(user);
    }
}