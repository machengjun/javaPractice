package com.example.auth.dao.mapper;

/**
 * @author 马成军
 **/
import org.apache.ibatis.annotations.Param;

/**
 * @author Piconjo
 * @date 2020/5/19  16:29
 */
public interface RoleMapper {
    int addRole(@Param("username") String username, @Param("name") String name);
}