package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class CompletableFutureShare {


    @Async
    public CompletableFuture count(int num) {
        for (int j = 0; j < 10000; j++) {
            log.info("{},{}", j, num);
            num++;
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture add(List<String> data) {
        data.add("sss");
        return CompletableFuture.completedFuture(null);
    }
}
