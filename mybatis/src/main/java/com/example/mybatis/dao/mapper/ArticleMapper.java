package com.example.mybatis.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Article;
import com.example.mybatis.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 马成军
 **/
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 获取任意一条文章，同时关联出作者信息
     *
     * @return
     */
    Article getArticleWithAuthor();

    /**
     * 分页获取一条文章，同时关联出作者信息
     *
     * @param page
     * @return
     */
    Page<Article> listArticleWithAuthor(IPage<User> page);

    /**
     * 通过author ID 获取文章
     *
     * @return
     */
    @Select("select * from article where #{authorId}")
    List<Article> findByAuthorId(@Param("authorId") String authorId);


    @Results({
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "author_id", property = "author",
                    one = @One(select = "com.example.demo.dao.mapper.AuthorMapper.selectByIdWithAddr"))
    })
    @Select("select * from article where id=#{id}")
    Article getThreelevel(String id);
}
