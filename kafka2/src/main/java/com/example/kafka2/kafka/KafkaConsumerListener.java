package com.example.kafka2.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka Feed数据消费端
 *
 * @author 马成军
 */
@Component
public class KafkaConsumerListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 消费站内信
     *
     * @param message
     */
    @KafkaListener(topics = {"${javaPractice.kafka.consumer.user-message.topic}"})
    public void userMessage(String message) throws InterruptedException {
        //接受kafka数据
        LOGGER.info("开始消费队列{}" ,message);
        Thread.sleep(5000);
        LOGGER.info("结束消费队列{}" ,message);
        JSONObject jsonObject = JSON.parseObject(message);
        String messageSource = (String) jsonObject.get("message_source");
        JSONObject data = (JSONObject) jsonObject.get("data");
        if(messageSource==null&&data==null){
            LOGGER.info("队列入参不合法");
            return;
        }

    }
    
//    /**
//     * 消费业务数据重新计算
//     *
//     * @param dataModify
//     */
//    @KafkaListener(topics = {"${javaPractice.kafka.consumer.data-modify.topic}"})
//    public void dataModify(String dataModify) {
//        //接受kafka数据
//        LOGGER.info("开始消费队列" + dataModify);
//        JSONObject jsonObject = JSON.parseObject(dataModify);
//        String type = (String) jsonObject.get("type");
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        if(type==null&&data==null){
//            LOGGER.info("队列入参不合法");
//            return;
//        }
//    }

}
