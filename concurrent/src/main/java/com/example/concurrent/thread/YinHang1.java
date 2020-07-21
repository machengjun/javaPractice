package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
class YinHang1 extends Thread {

    private double money = 5000;
    private CountDownLatch countDownLatch;

    public YinHang1(CountDownLatch countDownLatch) {
        log.info("{} 入参：{}", Thread.currentThread().getName(),countDownLatch);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < money; i++) {
            money -= 10;
            log.info("{} 剩余：{}", Thread.currentThread().getName(),money);
            if (money <= 0) {
                break;
            }
        }
        countDownLatch.countDown();
    }
}
