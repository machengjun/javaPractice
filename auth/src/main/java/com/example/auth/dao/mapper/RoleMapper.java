package com.example.auth.dao.mapper;

/**
 * @author 马成军
 **/

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.entity.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role> {
    Integer addRole(@Param("username") String username, @Param("name") String name);
}