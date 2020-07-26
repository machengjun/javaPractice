package com.example.kafka;


import com.alibaba.fastjson.JSONObject;
import com.example.kafka.kafka.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

/**
 * 测试生产队列
 *
 * @author 马成军
 **/
@SpringBootTest
public class KafkaProducerServiceTest {
    @Autowired
    KafkaProducerService kafkaProducerService;

    @Test
    public void sentEnergyWarning() {
        //预警
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("resource_id", "fe3c1e63dd7a81f6a189379860f85184");
        data.put("schema", "iot_FgMKiJsOaa");
        jsonObject.put("message_source", "energy_warning");
        jsonObject.put("data", data);
        String msg = jsonObject.toJSONString();
        kafkaProducerService.send("user-message2", msg);

    }


    @Test
    public void sentKpiReportDataModify() {
        //kpi
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("type","kpi");
        data.put("schema", "iot_FgMKiJsOaa");
        data.put("statistics_time", "2019-12");
        jsonObject.put("data", data);
        String msg = jsonObject.toJSONString();
        kafkaProducerService.send("data-modify", msg);
    }


}
