package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 马成军
 **/

public interface AuthorMapper extends BaseMapper<Author> {

    /**
     * 测试注解方式写负责查询
     * 对比发现还是用xml方式可读性更高，出错率低
     * @param ages
     * @return
     */
    @Select("<script>"
            + "select * "
            + "from author "
            + "where age in "
            + "<foreach item='item' collection='ages' separator=',' open='(' close=')' index=''>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    List<Author> complexSearch(@Param("ages") List<Integer> ages);
}
