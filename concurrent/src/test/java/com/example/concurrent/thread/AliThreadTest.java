package com.example.concurrent.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.example.concurrent.component.DsSchemaHolder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 阿里爸爸封装线程try（线程间变量传递）
 */
@Slf4j
@SpringBootTest
public class AliThreadTest {
    @Autowired
    CompletableFutureOne completableFutureOne;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolA;


    private static void doSomeThing(String s) {
        //模拟异常  默认注释状态
//        List<String> data = new ArrayList<>();
//        data.get(1);
        log.info("ThreadLocal 变量 {}", DsSchemaHolder.getSchema());
        System.out.println("doSomeThing start " + s);
        for (int i = 0; i < 100; i++) {
            System.out.println(s + " " + i);
            log.info(s+i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CompletableFuture ThreadPoolTaskExecutor 方式使用 线程变量继承
     *
     * @throws InterruptedException
     */
    @Test
    @Async(value = "ThreadPoolA")
    public void completableFuture() throws InterruptedException {
        Executors.newFixedThreadPool(2);

        DsSchemaHolder.setSchema("mcj schema");
        Executor service = TtlExecutors.getTtlExecutor(threadPoolA);
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            doSomeThing("hello");
            return "hello";
        }, service);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("world");
            return "world";
        }, service);

        CompletableFuture.supplyAsync(() -> {
            doSomeThing("china");
            return "china";
        }, service);

        Thread.sleep(100000);
    }

    /**
     * Callable 子线程继承 父线程线程变量 写法1
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void Callable() throws InterruptedException, ExecutionException {
        DsSchemaHolder.setSchema("mcj schema");
        ExecutorService service = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));
        //实例化线程，设置直播间名称，增长人数的速度（如：500毫秒增长1人）
        ZhiBoThreadLocal zhiBo1 = new ZhiBoThreadLocal("王者荣耀", 500);
        ZhiBoThreadLocal zhiBo2 = new ZhiBoThreadLocal("绝地求生", 300);

        Future<Integer> result1 = service.submit(zhiBo1);
        Future<Integer> result2 = service.submit(zhiBo2);
        //5秒后线程睡眠
        Thread.sleep(5000);
        //设置各个直播间人数停止增长
        zhiBo1.setStop(false);
        zhiBo2.setStop(false);

        //获取返回值
        int count1 = result1.get();
        int count2 = result2.get();
        //输出
        System.out.println(zhiBo1.getNameString() + "直播间有" + count1 + "人");
        System.out.println(zhiBo2.getNameString() + "直播间有" + count2 + "人");

        service.shutdownNow();//现在关闭

    }

    /**
     * Callable 子线程继承 父线程线程变量 写法2
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void Callable2() throws InterruptedException, ExecutionException {
        DsSchemaHolder.setSchema("mcj schema");
        ExecutorService service = Executors.newFixedThreadPool(2);
        //实例化线程，设置直播间名称，增长人数的速度（如：500毫秒增长1人）
        ZhiBoThreadLocal zhiBo1 = new ZhiBoThreadLocal("王者荣耀", 500);
        ZhiBoThreadLocal zhiBo2 = new ZhiBoThreadLocal("绝地求生", 300);
        Callable zhiBo1Callable = TtlCallable.get(zhiBo1);
        Future<Integer> result1 = service.submit(zhiBo1Callable);
        Callable zhiBo2Callable = TtlCallable.get(zhiBo2);
        Future<Integer> result2 = service.submit(zhiBo2Callable);
        //5秒后线程睡眠
        Thread.sleep(5000);
        //设置各个直播间人数停止增长
        zhiBo1.setStop(false);
        zhiBo2.setStop(false);
        //获取返回值
        int count1 = result1.get();
        int count2 = result2.get();
        //输出
        System.out.println(zhiBo1.getNameString() + "直播间有" + count1 + "人");
        System.out.println(zhiBo2.getNameString() + "直播间有" + count2 + "人");
        service.shutdownNow();//现在关闭

    }

}
