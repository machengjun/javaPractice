package com.example.saas.rest;

import com.example.saas.entity.User;
import com.example.saas.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;


    @GetMapping
    ResponseEntity<String> get() {
        User user = userService.getMyFriend();
        return new ResponseEntity("ok", HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<String> add() {
        User user = new User();
        user.setAge(11);
        user.setName("老胡");
        userService.insertMyFriend(user);
        return new ResponseEntity("ok", HttpStatus.OK);
    }


}
