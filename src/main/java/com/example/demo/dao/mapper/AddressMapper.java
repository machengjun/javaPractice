package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 马成军
 **/
public interface AddressMapper extends BaseMapper<Address> {

    @Select("select * from address where author_id = #{authorId}")
    List<Address> findByAuthorId(@Param("authorId") String authorId);

}
