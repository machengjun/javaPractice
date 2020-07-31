package com.example.concurrent.thread;

import com.example.concurrent.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
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
        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = completableFutureShare.count(num);
            completableFutureList.add(completableFuture);
        }
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
        //绕过lamba final属性
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

    /**
     * Atomic 控制原子性
     */
    @Test
    public void demo5() {
        AtomicInteger num = new AtomicInteger(0);
        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
//        synchronized(num){
        for (int i = 0; i < 10; i++) {
            CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < 10000; j++) {
                    num.addAndGet(1);
                }
            });
            completableFutureList.add(completableFuture);

        }
//        }
        CompletableFuture[] arr = completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]);
        CompletableFuture.allOf(arr).join();
        log.info("result: {}", num);

    }

    // 原子性引用
    private static AtomicReference<Person> aRperson;

    /**
     * Atomic 原子更新引用类型
     */
    @Test
    public void demo6() {

        Person person = new Person("Tom", 18);
        aRperson = new AtomicReference<>(person);

        System.out.println("Atomic Person is " + aRperson.get().toString());

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        CompletableFuture completableFuture1 = CompletableFuture.runAsync(() -> {
            aRperson.getAndSet(new Person("Tom1", aRperson.get().getAge() + 1));
            System.out.println("Thread1 Atomic References "
                    + aRperson.get().toString());
        });
        CompletableFuture completableFuture2 = CompletableFuture.runAsync(() -> {
            aRperson.getAndSet(new Person("Tom2", aRperson.get().getAge() + 1));
            System.out.println("Thread2 Atomic References "
                    + aRperson.get().toString());
        });
        CompletableFuture.allOf(completableFuture1, completableFuture2).join();

        System.out.println("Now Atomic Person is " + aRperson.get().toString());

    }


    /**
     * Atomic 原子更新引用类型 ABA问题
     */
    @Test
    public void demo7() {
        String str1 = "aaa";
        String str2 = "bbb";
        AtomicStampedReference<String> reference = new AtomicStampedReference<String>(str1, 1);
        reference.compareAndSet(str1, str2, reference.getStamp(), reference.getStamp() + 1);
        System.out.println("reference.getReference() = " + reference.getReference());

        boolean b = reference.attemptStamp(str2, reference.getStamp() + 1);
        System.out.println("b: " + b);
        System.out.println("reference.getStamp() = " + reference.getStamp());

        boolean c = reference.weakCompareAndSet(str2, "ccc", 4, reference.getStamp() + 1);
        System.out.println("reference.getReference() = " + reference.getReference());
        System.out.println("c = " + c);
    }


    /**
     * list线程安全 正确写法
     */
    @Test
    public void testSuccess() {
        //　　CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
        //   List<Person> personList = new CopyOnWriteArrayList<Person>();
        //锁机制
        List<Person> personList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 9; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Person person = new Person("name" + finalI, finalI);
                    personList.add(person);
                }
            }
            ).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print(personList);
    }

    public void print(List<Person> personList) {
        System.out.println(personList.size());
        personList.forEach(item -> {
            if (item == null) {
                System.out.println("值" + item);
            }
        });
    }

    @Test
    public void testError() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Person person = new Person("name" + finalI, finalI);
                    personList.add(person);
                }
            }
            ).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print(personList);
    }


    /**
     * 锁 class
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
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
