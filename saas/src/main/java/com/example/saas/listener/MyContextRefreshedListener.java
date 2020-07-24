package com.example.saas.listener;


import com.example.saas.DsService.DsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
/**
 * @author 马成军
 **/
@Configuration
public class MyContextRefreshedListener {
    private static final Logger log = LoggerFactory.getLogger(MyContextRefreshedListener.class);

    @Autowired
    private DsService dsService;
    private boolean isInit = false;

    public MyContextRefreshedListener() {
    }

    @EventListener
    void contextRefreshedEvent(ContextRefreshedEvent event) {
        log.info("contextRefreshedEvent...");
        if (!this.isInit) {
            this.dsService.refreshDsInfo();
            this.isInit = true;
        }

    }
}

