package com.example.mybatis.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author K
 * @since 2019/7/9
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {


    /**
     * 内置的选装件
     */
    int insertBatchSomeColumn(List<T> entityList);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    int deleteByIdWithFill(T entity);

    /**
     * 自己自定义
     */
    T findOne(Serializable id);

}
