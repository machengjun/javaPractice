package com.example.concurrent.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.concurrent.component.DsSchemaHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author 马成军
 **/
@Slf4j
public
class ZhiBoThreadLocal implements Callable<Integer> {
    private String nameString;//直播间名称
    private int peoplenum = 0;//直播间人数
    private boolean stop = true;//手动停止线程
    private long speed;//增长人数的速度

    public ZhiBoThreadLocal() {
    }//空构造器

    public ZhiBoThreadLocal(String nameString) {
        super();
        this.nameString = nameString;
    }

    public ZhiBoThreadLocal(String nameString, long speed) {
        super();
        this.nameString = nameString;
        this.speed = speed;
    }

    @Override
    public Integer call() throws Exception {//实现call方法
        //模拟异常
//        if ("王者荣耀".equals(this.getNameString())) {
//            List<String> data = new ArrayList<>();
//            data.get(1);
//        }
//        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
//        String value = context.get();
//        log.info("TransmittableThreadLocal {} 变量 {}",this.nameString,value);

        log.info("ThreadLocal {} 变量 {}", this.nameString, DsSchemaHolder.getSchema());

        while (stop) {
            Thread.sleep(speed);
            peoplenum++;
            log.info("{} 人数涨到{}", this.getNameString(), peoplenum);
        }
        return peoplenum;
    }

    public String getNameString() {
        return nameString;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}