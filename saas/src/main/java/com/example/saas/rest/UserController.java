package com.example.saas.rest;

import com.example.saas.entity.User;
import com.example.saas.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    ThreadPoolTaskExecutor threadPoolA;


    @GetMapping
    ResponseEntity<String> get() {
        User user = userService.getMyFriend();
        return new ResponseEntity(user, HttpStatus.OK);
    }


    @PostMapping
    ResponseEntity<String> add() {
        User user = new User();
        user.setAge(11);
        user.setName("老胡");
        userService.insertMyFriend(user);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    /**
     * 测试多线程 saas运行情况
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("thread")
    ResponseEntity<String> threadGetData() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CompletableFuture<User> res1 = CompletableFuture.supplyAsync(() -> {
            return userService.getMyFriend();
        }, service);
        CompletableFuture<User> res2 = CompletableFuture.supplyAsync(() -> {
            return userService.getMyFriend();
        }, service);
        CompletableFuture.allOf(res1, res2).join();
        List<User> users = new ArrayList<>();
        users.add(res1.get());
        users.add(res2.get());
        return new ResponseEntity(users, HttpStatus.OK);
    }

}
