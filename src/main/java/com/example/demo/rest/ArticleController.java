package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.mapper.ArticleMapper;
import com.example.demo.entity.Article;
import com.example.demo.req.ArticleCreateReq;
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
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleMapper articleMapper;


    @PostMapping
    ResponseEntity<String> add(@RequestBody ArticleCreateReq dto, BindingResult result) {
        log.debug(result.hasErrors() + "");
        log.debug("新增聚合文章 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        articleMapper.insert(article);
        return new ResponseEntity("添加成功", HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<String> get() {
        return new ResponseEntity("访问成功", HttpStatus.OK);
    }

}
