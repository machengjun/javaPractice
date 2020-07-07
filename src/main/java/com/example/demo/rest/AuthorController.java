package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.jpa.AuthorDao;
import com.example.demo.dao.mapper.ArticleMapper;
import com.example.demo.entity.Article;
import com.example.demo.entity.Author;
import com.example.demo.req.ArticleCreateReq;
import com.example.demo.req.AuthorCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorDao authorDao;


    @PostMapping
    ResponseEntity<String> add(@RequestBody AuthorCreateDto dto, BindingResult result) {
        log.debug(result.hasErrors() + "");
        log.debug("新增作者 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        //todo 属性拷贝
        Author author = new Author();
        author.setName(dto.getName());
        author.setAge(dto.getAge());
        authorDao.save(author);
        return new ResponseEntity("添加成功", HttpStatus.OK);
    }


}
