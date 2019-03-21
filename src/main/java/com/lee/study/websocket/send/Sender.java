package com.lee.study.websocket.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName Sender
 * @Description TODO
 * @Auth JussiLee
 * @Date 2019/3/18 14:16
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String content = "hello " + new Date();
        System.out.println("Sender: " + content);
        this.rabbitTemplate.convertAndSend("topicExchange","lee.topic.message", content);
        this.rabbitTemplate.convertAndSend("topicExchange","topic.lijiaxi.message", content);
    }
}
