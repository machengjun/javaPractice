package com.example.kafka.rest;

import com.alibaba.fastjson.JSONObject;
import com.example.kafka.kafka.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @GetMapping
    ResponseEntity<String> get(@RequestParam(value = "id") String id,@RequestParam(value = "topic") String topic)  {
        //预警
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("resource_id", id);
        data.put("schema", "iot_FgMKiJsOaa");
        jsonObject.put("message_source", "energy_warning");
        jsonObject.put("data", data);
        String msg = jsonObject.toJSONString();
        kafkaProducerService.send(topic, msg);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

}
