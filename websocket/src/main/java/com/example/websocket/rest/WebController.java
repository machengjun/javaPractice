package com.example.websocket.rest;

import com.example.websocket.config.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author 马成军
 **/
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping
    ResponseEntity<String> get() {
        return new ResponseEntity("welcome to websocket", HttpStatus.OK);
    }

    @GetMapping("msg")
    ResponseEntity<String> sentMsg(@RequestParam(value = "data", required = false, defaultValue = "hello world") String data) throws IOException {
        WebSocketSession webSocketSession = WsSessionManager.get("mcjToken");
        if (null != webSocketSession)
            webSocketSession.sendMessage(new TextMessage(data + LocalDateTime.now().toString()));
        return new ResponseEntity("msg send success", HttpStatus.OK);
    }
}
