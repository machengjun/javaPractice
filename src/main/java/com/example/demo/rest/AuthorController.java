package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.jpa.AuthorDao;
import com.example.demo.dao.mapper.AuthorMapper;
import com.example.demo.entity.Author;
import com.example.demo.req.AuthorCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    AuthorMapper authorMapper;


    @PostMapping
    ResponseEntity<String> add(@RequestBody AuthorCreateDto dto, BindingResult result) {
        log.debug(result.hasErrors() + "");
        log.debug("新增作者 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Author author = new Author();
        //属性拷贝
        BeanUtils.copyProperties(dto, author);
        authorDao.save(author);
        return new ResponseEntity("添加成功", HttpStatus.OK);
    }


    /**
     * get 传数组
     *
     * @param ages
     * @return
     */
    @GetMapping
    ResponseEntity<String> get(@RequestParam(value = "age", required = false) List<Integer> ages) {
        List<Author> authorList = authorMapper.complexSearch(ages);
        return new ResponseEntity(authorList, HttpStatus.OK);
    }


}
