package com.example.kafka.listener;

import com.example.kafka.even.UserMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 用户消息事件队列监听器
 *
 * @author 马成军
 **/
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class UserMessageListener implements ApplicationListener<UserMessageEvent> {

    @Override
    public void onApplicationEvent(UserMessageEvent userMessageEvent) {
        log.info("预警消息开始处理{}",userMessageEvent);
    }


}


