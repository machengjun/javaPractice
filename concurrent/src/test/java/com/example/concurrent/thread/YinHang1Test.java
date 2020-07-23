package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

/**
 * 多线程常用基本 写法
 */
@Slf4j
@SpringBootTest
class YinHang1Test {

    @Test
    public void yinHang1() throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(2);

        Thread thread1 = new YinHang1(countDown);
        thread1.setName("工商");
        thread1.start();
        Thread thread2 = new YinHang1(countDown);
        thread2.start();
        log.info("异步全部启动完毕");
        countDown.await();
        log.info("异步全部执行完毕");

    }

    @Test
    public void yinHang2() {
        Runnable yinhang = new YinHang2(10);//设置每次取1000
        Thread xiaoming = new Thread(yinhang, "小明");//实例线程，并命名
        Thread xiaoli = new Thread(yinhang, "小丽");
        Thread xiaogang = new Thread(yinhang, "小刚");
        Thread xiaohong = new Thread(yinhang, "小红");
        xiaoming.start();//启动线程
        xiaoli.start();
        xiaogang.start();
        xiaohong.start();
    }

    @Test
    public void zhiBo() throws InterruptedException, ExecutionException {

        //设置五个线程
        ExecutorService service = Executors.newFixedThreadPool(5);
        //实例化线程，设置直播间名称，增长人数的速度（如：500毫秒增长1人）
        ZhiBo zhiBo1 = new ZhiBo("王者荣耀", 500);
        ZhiBo zhiBo2 = new ZhiBo("绝地求生", 300);
        ZhiBo zhiBo3 = new ZhiBo("英雄联盟", 50);
        ZhiBo zhiBo4 = new ZhiBo("部落冲突", 200);
        ZhiBo zhiBo5 = new ZhiBo("美女", 10);
        //Future 相当于是用来存放Executor执行的结果的一种容器
        Future<Integer> result1 = service.submit(zhiBo1);
        Future<Integer> result2 = service.submit(zhiBo2);
        Future<Integer> result3 = service.submit(zhiBo3);
        Future<Integer> result4 = service.submit(zhiBo4);
        Future<Integer> result5 = service.submit(zhiBo5);
        //5秒后线程睡眠
//        Thread.sleep(5000);
        //设置各个直播间人数停止增长
        zhiBo1.setStop(false);
        zhiBo2.setStop(false);
        zhiBo3.setStop(false);
        zhiBo4.setStop(false);
        zhiBo5.setStop(false);

        //获取返回值
        int count1 = result1.get();
        int count2 = result2.get();
        int count3 = result3.get();
        int count4 = result4.get();
        int count5 = result5.get();
        //输出
        System.out.println(zhiBo1.getNameString() + "直播间有" + count1 + "人");
        System.out.println(zhiBo2.getNameString() + "直播间有" + count2 + "人");
        System.out.println(zhiBo3.getNameString() + "直播间有" + count3 + "人");
        System.out.println(zhiBo4.getNameString() + "直播间有" + count4 + "人");
        System.out.println(zhiBo5.getNameString() + "直播间有" + count5 + "人");

        service.shutdownNow();//现在关闭
    }

}