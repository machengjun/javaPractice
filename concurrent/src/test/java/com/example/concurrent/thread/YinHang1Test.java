package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

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
    public void yinHang2(){
        Runnable yinhang=new YinHang2(10);//设置每次取1000
        Thread xiaoming=new Thread(yinhang,"小明");//实例线程，并命名
        Thread xiaoli=new Thread(yinhang,"小丽");
        Thread xiaogang=new Thread(yinhang,"小刚");
        Thread xiaohong=new Thread(yinhang,"小红");
        xiaoming.start();//启动线程
        xiaoli.start();
        xiaogang.start();
        xiaohong.start();
    }

}