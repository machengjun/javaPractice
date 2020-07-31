package com.example.auth.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.Role;
import com.example.auth.entity.User;

import java.util.List;

/**
 * @author 马成军
 **/
public interface UserMapper extends BaseMapper<User> {

    User findUserByUsername(String username);

    List<Role> findRoleByUsername(String email);

    int addUser(User user);
}