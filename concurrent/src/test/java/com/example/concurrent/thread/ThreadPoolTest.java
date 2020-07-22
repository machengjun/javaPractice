package com.example.concurrent.thread;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 马成军
 **/
@SpringBootTest
public class ThreadPoolTest {

    @Test
    public void sample() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj001");
            return "mcj001";
        }, executor);
        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj002");
            return "mcj002";
        }, executor);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj003");
            return "mcj003";
        }, executor);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj004");
            return "mcj004";
        }, executor);

        Thread.sleep(100000);
    }


    private static void doSomeThing(String s) {
        //模拟异常  默认注释状态
//        List<String> data = new ArrayList<>();
//        data.get(1);
        System.out.println("doSomeThing start " + s);
        for (int i = 0; i < 100; i++) {
            System.out.println(s + " " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
