package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
public class CompletableFutureTest {
    @Autowired
    CompletableFutureOne completableFutureOne;

    private static String get() {
        System.out.println("supplyAsync start");
        for (int i = 0; i < 100; i++) {
            System.out.println("supplyAsync " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "mcj";
    }

    @Test
    public void thenApply() {

        String result = CompletableFuture
                .supplyAsync(
                        CompletableFutureTest::get
                )
                .thenApply(s -> {
                            System.out.println("thenApply start" + s);
                            for (int i = 0; i < 100; i++) {
                                System.out.println("thenApply " + i);
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return "haha";
                        }
                )
                .join();
        System.out.println(result);
    }

    @Test
    public void myCompletableFutureOne() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<String> page1 = completableFutureOne.findUser("PivotalSoftware", 300);
        CompletableFuture<String> page2 = completableFutureOne.findUser("CloudFoundry", 100);
        CompletableFuture<String> page3 = completableFutureOne.findUser("Spring-Projects", 100);

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        completableFutureList.add(page1);
        completableFutureList.add(page2);
        completableFutureList.add(page3);
//        CompletableFuture arr[] ={page1,page2,page3};
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        //只要有一个线程完成就往下走
        CompletableFuture.anyOf(arr).exceptionally(e -> {
            log.info(e.getMessage());
            return e;
        });
        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        for (CompletableFuture<String> com : completableFutureList) {
            try {
                String res = com.get();
                log.info("--> " + res);
            } catch (Throwable e) {
                log.info("get:{}",e.getMessage());
            }
        }
        log.info("end");

    }

    @Test
    public void myCompletableFutureTwo() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<String> page1 = completableFutureOne.findUser("PivotalSoftware", 100);
        CompletableFuture<String> page2 = completableFutureOne.findUser("CloudFoundry", 100);
        CompletableFuture<String> page3 = completableFutureOne.findUser("Spring-Projects", 100);

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        completableFutureList.add(page1);
        completableFutureList.add(page2);
        completableFutureList.add(page3);
        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
    }


}
