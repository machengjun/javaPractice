package com.example.kafka.kafka.config;


import com.example.kafka.kafka.KafkaConsumerListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;


/**
 * Kafka消费端配置
 *
 * @author 马成军
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${javaPractice.kafka.consumer.servers}")
    private String servers;
    @Value("${javaPractice.kafka.consumer.group-id}")
    private String groupId;
    @Value("${javaPractice.kafka.consumer.max-poll}")
    private String maxPoll;

    /**
     *  消费者批量工程
     */
    @Bean
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> setting = consumerConfigs();
        //设置为批量消费,Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        setting.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,maxPoll);
        DefaultKafkaConsumerFactory<Object, Object> kafkaConsumer = new DefaultKafkaConsumerFactory<>(setting);
        factory.setConsumerFactory(kafkaConsumer);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }


    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return propsMap;
    }


    /**
     * kafka监听
     *
     * @return
     */
    @Bean
    public KafkaConsumerListener listener() {
        return new KafkaConsumerListener();
    }
}


