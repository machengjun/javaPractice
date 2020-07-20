package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dao.mapper.ArticleMapper;
import com.example.mybatis.entity.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleMapperTest {

    @Autowired
    ArticleMapper articleMapper;


    /**
     * many to one 查询 xml映射结果
     */
    @Test
    public void getArticleWithAuthor() {
        Article article = articleMapper.getArticleWithAuthor();
        Assertions.assertNotNull(article);
    }


    /**
     * many to one 查询 3层嵌套
     */
    @Test
    public void getThreelevel() {
        Article article = articleMapper.getThreelevel("8d09f56900ca4fef7342b392d33c7d72");
        Assertions.assertNotNull(article);
    }


    /**
     * many to one 查询 xml映射结果 list 分页
     */
    @Test
    public void listArticleWithAuthor() {
        Page page = new Page(1, 10);
        Page<Article> articlePage = articleMapper.listArticleWithAuthor(page);
        Assertions.assertNotNull(articlePage);
    }



}