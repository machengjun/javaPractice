package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池参数策略试验
 *
 * @author 马成军
 **/
@SpringBootTest
@Slf4j
public class ThreadPoolTest {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolA;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @Async(value = "ThreadPoolA")
    public void sample() throws InterruptedException, ExecutionException {
        threadPoolA.setCorePoolSize(2);
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj001");
            return "mcj001";
        }, threadPoolA);
        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj002");
            return "mcj002";
        }, threadPoolA);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj003");
            return "mcj003";
        }, threadPoolA);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("mcj004");
            return "mcj004";
        }, threadPoolA);

        CompletableFuture<String> data = res.exceptionally((e) -> {
            log.info("exceptionally " + e.getMessage());
            return "err mcj";
        }).thenApply((s) -> {
            log.info("exceptionally return   {}", s);
            return "thenApply res";
        });
        log.info("data   {}", data);
        try {
            String data2 = res.get();
            log.info("data2   {}", data2);
        } catch (Throwable e) {
            log.info("get " + e.getMessage());
        }

        Thread.sleep(100000);
    }

    /**
     * 多线程 核心线程数 任务完成速度测试
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    @Async(value = "ThreadPoolA")
    public void setting() throws InterruptedException, ExecutionException {
        threadPoolA.setCorePoolSize(4);
        long starttime = System.currentTimeMillis();
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String name = "mcj00" + i;
            CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
                countThing(name);
                return name;
            }, threadPoolA);
            completableFutureList.add(res);
        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        BlockingQueue<Runnable> que = threadPoolA.getThreadPoolExecutor().getQueue();
        log.info("que:{}", que);
        CompletableFuture.allOf(arr).join();
        long endtime = System.currentTimeMillis();
        log.info("程序运行的时间为:{}毫秒", (endtime - starttime));

    }

    private static void countThing(String s) {
        log.info("countThing start {}", s);
        for (int i = 0; i < 10000; i++) {
            System.out.println(s + " " + i);
            log.info("{} {}", s, i);
        }
    }

    private static void doSomeThing(String s) {
        //模拟异常  默认注释状态
        List<String> data = new ArrayList<>();
        data.get(1);
        log.info("doSomeThing start {}", s);
        for (int i = 0; i < 100; i++) {
            System.out.println(s + " " + i);
            log.info("{} {}", s, i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void policy() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 0,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<Runnable>(2),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 6; i++) {
            System.out.println("添加第" + i + "个任务");
            executor.execute(new MyThread("线程" + i));
            Iterator iterator = executor.getQueue().iterator();
            while (iterator.hasNext()) {
                MyThread thread = (MyThread) iterator.next();
                System.out.println("列表：" + thread.name);
            }
        }
    }

    @Test
    @Async(value = "ThreadPoolA")
    public void rongDuan() throws InterruptedException, ExecutionException {
        threadPoolA.setCorePoolSize(10);
        long starttime = System.currentTimeMillis();
        List<CompletableFuture> completableFutureList = new ArrayList<>();
        for (int i = 1; i <= 1010; i++) {
            String name = "mcj00" + i;
            CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> restTemplate.getForObject("http://127.0.0.1:21002/discovery/feignEcho/" + name, String.class), threadPoolA);
            completableFutureList.add(res);
        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        long endtime = System.currentTimeMillis();
        log.info("程序运行的时间为:{}毫秒", (endtime - starttime));

    }

    static class MyThread implements Runnable {
        String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程:" + Thread.currentThread().getName() + " 执行:" + name + "  run");
        }
    }

}
