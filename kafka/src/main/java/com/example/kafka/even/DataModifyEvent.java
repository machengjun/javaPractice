package com.example.kafka.even;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationEvent;

/**
 * 数据重新计算模块时间
 *
 * @author 马成军
 **/

public class DataModifyEvent extends ApplicationEvent {
    private String type;
    private JSONObject data;

    public DataModifyEvent(Object source, String type, JSONObject data) {
        super(source);
        this.type = type;
        this.data = data;
    }
}
