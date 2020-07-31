package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * 多线程变量安全
 */
@Slf4j
@SpringBootTest
public class ShareValueTest {

    @Autowired
    private static int count = 0;

    @Autowired
    CompletableFutureShare completableFutureShare;

    @Test
    public void demo1() {
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();

        Integer num = 0;
//        synchronized(num){
        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = completableFutureShare.count(num);
            completableFutureList.add(completableFuture);
        }
//        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("result: {}", num);

    }

    @Test
    public void demo3() {
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();

        List<String> data = new ArrayList<>();
//        synchronized(num){
        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = completableFutureShare.add(data);
            completableFutureList.add(completableFuture);
        }
//        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("result: {}", data);

    }

    /**
     * 锁任意对象demo
     */
    @Test
    public void demo4() {
        HashMap<String, Integer> num = new HashMap<>();
        num.put("key", 0);

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                synchronized (num) {
                    for (int j = 0; j < 10000; j++) {
                        Integer val = num.get("key") + 1;
                        num.put("key", val);
                    }
                }
            });
            completableFutureList.add(completableFuture);
        }

        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("result: {}", num);

    }

    @Test
    public void demo5() {
        List<String> stringList = new ArrayList<>();
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();

        List<String> data = new ArrayList<>();
//        synchronized(num){
        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                stringList.add("ssddf");
            });
            completableFutureList.add(completableFuture);

        }
//        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("result: {}", data);

    }


    @Test
    public void demo2() throws InterruptedException, ExecutionException {

        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(() -> run());
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);

    }


    public void run() {
        //同步锁防止冲突
        synchronized (ShareValueTest.class) {
            for (int i = 0; i < 1000000; i++)
                count++;
        }
    }

}
