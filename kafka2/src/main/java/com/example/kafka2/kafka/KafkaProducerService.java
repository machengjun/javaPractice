package com.example.kafka2.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Kafka 发送告警记录服务端
 *
 * @author 马成军
 **/
@Slf4j
@Service
public class KafkaProducerService {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object message) {
        log.info("发送告警记录到kafka-topic={}", topic);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new FutureCallBack(message));
    }

    public static class FutureCallBack implements ListenableFutureCallback<SendResult<String, Object>> {

        private Object message;

        public FutureCallBack(Object message) {
            this.message = message;
        }

        @Override
        public void onFailure(Throwable ex) {
            log.error("Unable to publish to kafka with message: {}, errorMessage: {}", message,
                    ex.getMessage());
        }

        @Override
        public void onSuccess(SendResult<String, Object> result) {
            log.info("Successfully publish to kafka with message: {}", message);
        }
    }
}
