package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CompletableFutureOne {


    @Async
    public CompletableFuture<String> findUser(String pivotalSoftware, int number) {
        //模拟异常
        if ("CloudFoundry".equals(pivotalSoftware)) {
            List<String> data = new ArrayList<>();
            data.get(1);
        }
        System.out.println(pivotalSoftware + "start");
        for (int i = 0; i < number; i++) {
            System.out.println(pivotalSoftware + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return CompletableFuture.completedFuture(pivotalSoftware);
    }
}
