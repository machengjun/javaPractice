package com.example.concurrent.rest;

import com.example.concurrent.thread.ZhiBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/concurrent")
public class ConcurrentController {

    @GetMapping
    ResponseEntity<String> get() throws InterruptedException {
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
        Thread.sleep(2000);
        //设置各个直播间人数停止增长
        zhiBo1.setStop(false);
        zhiBo2.setStop(false);
        zhiBo3.setStop(false);
        zhiBo4.setStop(false);
        zhiBo5.setStop(false);
        //获取返回值
        try {
            int count1 = result1.get(100,TimeUnit.SECONDS);


            int count2 = result2.get(100,TimeUnit.SECONDS);//堵塞操作，CompletableFuture 可以优化
            int count3 = result3.get(100,TimeUnit.SECONDS);
            int count4 = result4.get(100,TimeUnit.SECONDS);
            int count5 = result5.get(100,TimeUnit.SECONDS);
            //输出
            System.out.println(zhiBo1.getNameString() + "直播间有" + count1 + "人");
            System.out.println(zhiBo2.getNameString() + "直播间有" + count2 + "人");
            System.out.println(zhiBo3.getNameString() + "直播间有" + count3 + "人");
            System.out.println(zhiBo4.getNameString() + "直播间有" + count4 + "人");
            System.out.println(zhiBo5.getNameString() + "直播间有" + count5 + "人");
        } catch (Throwable e) {
            //注意捕捉异常
            log.info(e.getMessage());
        }
        service.shutdownNow();//现在关闭
        return new ResponseEntity("ok", HttpStatus.OK);
    }

}
