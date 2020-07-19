package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 马成军
 **/
public interface UserMapper extends MyBaseMapper<User> {


    List<User> getSomeByWrapper(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    List<User> getAgeIn(@Param("ages") List<String> ages);

    IPage<User> getAgeIn(IPage<User> page, @Param("ages") List<String> ages);

    @Update("update user set name = 'mcjs' where id=#{id}")
    int updateMcj(String id);
}
