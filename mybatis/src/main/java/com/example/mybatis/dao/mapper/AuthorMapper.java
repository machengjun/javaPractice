package com.example.mybatis.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Author;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 马成军
 **/

public interface AuthorMapper extends BaseMapper<Author> {

    /**
     * 测试注解方式写负责查询
     * 对比发现还是用xml方式可读性更高，出错率低
     *
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

    /**
     * 指定id获取作者
     *
     * @return
     */

    Author getAuthorWithArticle(@Param("id") String id);

    Page<Author> listAuthorWithArticle(Page page);

    /**
     * 注解方式 关联
     * @param page
     * @return
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "articles", many = @Many(select = "com.example.demo.dao.mapper.ArticleMapper.se"))
    })
    @Select("SELECT * FROM author ")
    Page<Author> listAuthorWithArticle2(Page page);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "addresses", many = @Many(select = "com.example.demo.dao.mapper.AddressMapper.findByAuthorId"))
    })
    @Select("select * from author where id = #{id}")
    List<Author> selectByIdWithAddr(@Param("id") String id);

}
