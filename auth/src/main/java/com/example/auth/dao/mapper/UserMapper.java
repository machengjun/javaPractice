package com.example.auth.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.Role;
import com.example.auth.entity.User;

import java.util.List;

/**
 * @author 马成军
 **/

/**
 * @author Piconjo
 * @date 2020/5/19  16:29
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserByUsername(String email);

    List<Role> findRoleByUsername(String email);

    int addUser(User user);
}