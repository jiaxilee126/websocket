package com.lee.study.websocket.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName Render
 * @Description TODO
 * @Auth JussiLee
 * @Date 2019/3/21 8:37
 */
@Controller
public class Render {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/send/{msg}")
    @ResponseBody
    public String send(@PathVariable String msg){
        amqpTemplate.convertAndSend(
                "topic-publish",
                "public.websocket",
                msg);
        return "发送消息 === <"+ msg +"> 成功";
    }
}
