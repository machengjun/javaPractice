package com.example.validate.rest;

import com.alibaba.fastjson.JSON;
import com.example.validate.req.ArticleCreateReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @PostMapping
    ResponseEntity<String> add(@RequestBody @Validated ArticleCreateReq dto) {
        return new ResponseEntity("添加成功", HttpStatus.OK);
    }

    @PostMapping("sale")
    ResponseEntity<String> sale(@RequestBody @Validated ArticleCreateReq dto, BindingResult result) {
        log.debug("新增聚合文章 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("添加成功", HttpStatus.OK);
    }

}
