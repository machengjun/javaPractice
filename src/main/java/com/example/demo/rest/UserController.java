package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.jpa.AuthorDao;
import com.example.demo.dao.mapper.AuthorMapper;
import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.entity.Author;
import com.example.demo.entity.User;
import com.example.demo.req.AuthorCreateDto;
import com.example.demo.req.UserCreateDto;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;


    @PostMapping("service")
    ResponseEntity<String> add(@RequestBody UserCreateDto dto, BindingResult result) {
        log.debug(result.hasErrors() + "");
        log.debug("新增user 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        //属性拷贝
        BeanUtils.copyProperties(dto, user);
        Boolean res = userService.save(user);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<String> updata(@PathVariable("id") String id, @RequestBody UserCreateDto dto, BindingResult result) {
        log.debug(result.hasErrors() + "");
        log.debug("新增user 入参：" + JSON.toJSONString(dto));
        if (result.hasErrors()) {
            return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        //属性拷贝
        user.setId(id);
        BeanUtils.copyProperties(dto, user);
        int res = userMapper.updateById(user);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    /**
     * 测试乐观锁
     *
     * @return
     */
    @GetMapping("lock")
    ResponseEntity<String> lock() {
        String id = "c4e1b8601b3f907dc799d0fb1609516c";
        User user = userService.getById(id);
        user.setName("sfsdfs");
//        int version = 2;
//        User u = new User();
//        u.setId(id);
////        u.setVersion(version);
//        u.setName("mcss");
        if (userService.updateById(user)) {
            System.out.println("Update successfully");
        } else {
            System.out.println("Update failed due to modified by others");
        }
        return new ResponseEntity("ok", HttpStatus.OK);
    }

}
