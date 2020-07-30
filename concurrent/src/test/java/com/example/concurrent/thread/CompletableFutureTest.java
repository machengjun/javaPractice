package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuture使用方法的测试，伪需求代码编写
 */
@Slf4j
@SpringBootTest
public class CompletableFutureTest {
    @Autowired
    CompletableFutureOne completableFutureOne;


    /**
     * 简单异步测试
     *
     * @throws InterruptedException
     */
    @Test
    public void sample() throws InterruptedException {
        String name = "hello";
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            doSomeThing(name);
            return "ss";
        });
        CompletableFuture.supplyAsync(() -> {
            doSomeThing("world");
            return "ss";
        });

        Thread.sleep(10000);

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

    /**
     * runAsync 测试
     *
     * @throws Exception
     */
    @Test
    public void runAsync() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            doSomeThing("runAsync");
        });
        future.get();
        Thread.sleep(10000);

    }

    /**
     * supplyAsync 测试
     *
     * @throws Exception
     */
    @Test
    public void supplyAsync() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            doSomeThing("runAsync");
            return "mcj";
        });
        String data = future.get();
        System.out.println("data = " + data);
        Thread.sleep(10000);
    }

    /**
     * 测试方法回掉
     *
     * @throws Exception
     */
    @Test
    public void whenComplete() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            doSomeThing("whenComplete");
            System.out.println("run end ...");
        });

        //注意 就算出现异常也视为 完成
        future.whenComplete(
                (t, action) -> System.out.println("执行完成！"));
        //    future.whenComplete(new BiConsumer<Void, Throwable>() {
        //        @Override
        //        public void accept(Void t, Throwable action) {
        //            System.out.println("执行完成！");
        //        }
        //
        //    });
        future.exceptionally(t -> {
                    System.out.println("执行失败！" + t.getMessage());
                    return null;
                }
        );
        //    future.exceptionally(new Function<Throwable, Void>() {
        //        @Override
        //        public Void apply(Throwable t) {
        //            System.out.println("执行失败！"+t.getMessage());
        //            return null;
        //        }
        //    });
        Thread.sleep(10000);
    }


    @Test
    public void thenApply() throws Exception {
//        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
//            @Override
//            public Long get() {
//                long result = new Random().nextInt(100);
//                System.out.println("result1="+result);
//                return result;
//            }
//        }).thenApply(new Function<Long, Long>() {
//            @Override
//            public Long apply(Long t) {
//                long result = t*5;
//                System.out.println("result2="+result);
//                return result;
//            }
//        });
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
            long result = new Random().nextInt(100);
            System.out.println("result1=" + result);
            return result;
        }).thenApply(t -> {
            long result = t * 5;
            System.out.println("result2=" + result);
            return result;
        });
        long result = future.get();
        System.out.println(result);
    }


    /**
     * handle 方法和 thenApply 方法处理方式基本一样。不同的是 handle 是在任务完成后再执行，还可以处理异常的任务。thenApply 只可以执行正常的任务，任务出现异常则不执行 thenApply 方法
     *
     * @throws Exception
     */
    @Test
    public void handle() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 10 / 0;
            return new Random().nextInt(10);
        }).handle((param, throwable) -> {
            int result = -1;
            if (throwable == null) {
                result = param * 2;
            } else {
                System.out.println(throwable.getMessage());
            }
            return result;
        });
        System.out.println(future.get());
    }


    /**
     * 只是消费执行完成的任务，并可以根据上面的任务返回的结果进行处理。并没有后续的输错操作
     *
     * @throws Exception
     */
    @Test
    public void thenAccept() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> new Random().nextInt(10)).thenAccept(integer -> {
            System.out.println(integer);
        });
        future.get();
    }

    /**
     * 把两个CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理
     *
     * @throws Exception
     */
    @Test
    public void thenCombine() throws Exception {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> result = future1.thenCombine(future2, (t, u) -> t + " " + u);
        System.out.println(result.get());
    }

    /**
     * 和 thenCombine 类似 只是没有返回值
     *
     * @throws Exception
     */
    @Test
    public void thenAcceptBoth() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("f1=" + t);
            return t;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("f2=" + t);
            return t;
        });
        f1.thenAcceptBoth(f2, (t, u) -> System.out.println("f1=" + t + ";f2=" + u + ";"));
    }


    /**
     * 两个CompletionStage，谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的转化操作
     *
     * @throws Exception
     */
    public void applyToEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = new Random().nextInt(3);
                try {
                    TimeUnit.SECONDS.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("f1=" + t);
                return t;
            }
        });
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int t = new Random().nextInt(3);
                try {
                    TimeUnit.SECONDS.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("f2=" + t);
                return t;
            }
        });

        CompletableFuture<Integer> result = f1.applyToEither(f2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer t) {
                System.out.println(t);
                return t * 2;
            }
        });

        System.out.println(result.get());
    }

    /**
     * 两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）
     *
     * @throws Exception
     */
    public void runAfterEither() throws Exception {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("f1=" + t);
            return t;
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("f2=" + t);
            return t;
        });
        f1.runAfterEither(f2, () -> System.out.println("上面有一个已经完成了。"));
    }

    /**
     * 对两个 CompletionStage 进行流水线操作，第一个操作完成时，将其结果作为参数传递给第二个操作
     *
     * @throws Exception
     */
    public void thenCompose() throws Exception {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            int t = new Random().nextInt(3);
            System.out.println("t1=" + t);
            return t;
        }).thenCompose(param -> CompletableFuture.supplyAsync(() -> {
            int t = param * 2;
            System.out.println("t2=" + t);
            return t;
        }));
        System.out.println("thenCompose result : " + f.get());
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
                log.info("get:{}", e.getMessage());
            }
        }
        log.info("end");
    }


    /**
     * 使用线程池
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void pool() throws InterruptedException {
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

    @Test
    public void allOfRun() {

        List<String> data = new ArrayList<>();

        List<CompletableFuture> completableFutureList = new ArrayList<>();
        CompletableFuture completableFuture1 = CompletableFuture.runAsync(() -> {
            doSomeThing("ss");
            data.add("ss");
        });
        completableFutureList.add(completableFuture1);
        CompletableFuture completableFuture2 = CompletableFuture.runAsync(() -> {
            doSomeThing("bb");
            data.add("bb");

        });
        completableFutureList.add(completableFuture2);
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("{}", data);
    }

    @Test
    public void myCompletableFutureTow() throws ExecutionException, InterruptedException {
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
        CompletableFuture.allOf(arr);
        //添加join 有异常会抛出异常不用下面捕获
//        try {
//            CompletableFuture.allOf(arr).join();
//        }catch (Throwable e){
//            log.info(e.getMessage());
//        }

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        for (CompletableFuture<String> com : completableFutureList) {
            try {
                String res = com.get();
                log.info("--> " + res);
            } catch (Throwable e) {
                log.info("get:{}", e.getMessage());
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
