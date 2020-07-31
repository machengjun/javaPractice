package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 多线程变量安全
 */
@Slf4j
@SpringBootTest
public class ShareValueTest {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolA;
    private static int count = 0;

    @Test
    public void demo1() throws InterruptedException, ExecutionException {
        Integer num = 0;
//        synchronized(num){
            for (int i = 0; i < 10; i++) {
                CompletableFuture.runAsync(() -> {
                            runWithData(num);
                        }
                );
            }
//        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);

    }

    private void runWithData(Integer num) {
        for (int j = 0; j < 1000000; j++) {
            log.info("{},{}",j,num);
            count++;
        }
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
