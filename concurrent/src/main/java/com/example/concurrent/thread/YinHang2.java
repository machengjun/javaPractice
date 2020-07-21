package com.example.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class YinHang2 implements Runnable {
    private double money = 10000;//有一万元存款
    private double getmoney;//每次取多少
    private String data;


    public YinHang2() {
    }

    public YinHang2(double getmoney) {
        this.getmoney = getmoney;//每次取多少
        log.info("{} 入参：{}", Thread.currentThread().getName(), getmoney);
    }

    @Override
    public void run() {
        //实现run方法
        for (int i = 0; i < money; i++) {
            money -= getmoney;//总存款减去取出金额
            log.info("{} 取钱后，剩下：{}", Thread.currentThread().getName(), getmoney);
            if (money <= 0) {
                break;//跳出循环
            }
        }
    }
}
