package com.example.kafka.even;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationEvent;

/**
 * 用户消息事件参数
 *
 * @author 马成军
 */
public class UserMessageEvent extends ApplicationEvent {

    /**
     * 消息来源类型
     */
    private String messageSource;

    /**
     * 对应消息模块入参数据
     */
    private JSONObject data;


    /**
     * 传递参数
     *
     * @param source
     * @param messageSource
     * @param data
     */

    public UserMessageEvent(Object source, String messageSource, JSONObject data) {
        super(source);
        this.messageSource = messageSource;
        this.data = data;
    }
}
