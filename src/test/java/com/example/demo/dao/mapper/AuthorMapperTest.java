package com.example.demo.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorMapperTest {

    @Autowired
    AuthorMapper authorMapper;


    /**
     * many to one 查询 xml映射结果
     */
    @Test
    public void getAuthorWithArticle() {
        Author author = authorMapper.getAuthorWithArticle("dacefd7a-f09d-4e58-83a9-a9849031966d");
        Assertions.assertNotNull(author);
    }


    /**
     * many to one 查询 xml映射结果 分页 （xml配置方式）
     */
    @Test
    public void listAuthor() {
        Page page = new Page(1, 10);
        Page<Author> pageAuthor = authorMapper.listAuthorWithArticle(page);
        Assertions.assertNotNull(pageAuthor);
    }


    /**
     * many to one 查询 xml映射结果 分页( many 注解方式实现)
     */
    @Test
    public void listAuthor2() {
        Page page = new Page(1, 10);
        Page<Author> pageAuthor = authorMapper.listAuthorWithArticle2(page);
        Assertions.assertNotNull(pageAuthor);
    }

}