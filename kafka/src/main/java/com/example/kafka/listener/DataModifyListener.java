package com.example.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.example.kafka.even.DataModifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class DataModifyListener implements ApplicationListener<DataModifyEvent> {


    @Override
    public void onApplicationEvent(DataModifyEvent dataModifyEvent) {
        log.info("开始处理数据重新计算");
        log.info("DataModifyListener {}",JSON.toJSONString(dataModifyEvent));

    }



}


