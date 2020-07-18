package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 马成军
 **/
public interface UserMapper extends BaseMapper<User> {


    List<User> getSomeByWrapper(@Param(Constants.WRAPPER) QueryWrapper<User> queryWrapper);

    List<User> getAgeIn(@Param("ages") List<String> ages);

    IPage<User> getAgeIn(IPage<User> page, @Param("ages") List<String> ages);
}
