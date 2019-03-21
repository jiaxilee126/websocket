package com.lee.study.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName DefineWebsocket
 * @Description TODO
 * @Auth JussiLee
 * @Date 2019/3/20 11:47
 */
@ServerEndpoint("/websocket")
@Component
@Slf4j
public class DefineWebsocket {


    private Session session;

    private static CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<DefineWebsocket> websockets = new CopyOnWriteArraySet<>();

    public static synchronized void addOnlineCount() {
        DefineWebsocket.onlineCount ++ ;
    }

    public static synchronized int getOnlineCount() {
        return DefineWebsocket.onlineCount;
    }

    public static synchronized void subOnlineCount() {
        DefineWebsocket.onlineCount --;
    }
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sessions.add(session);
        websockets.add(this);
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为 = " + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        sessions.remove(this.session);
        websockets.remove(this);
        subOnlineCount();
        log.info("有一个连接关闭！当前在线人数为 = "+getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session){

    }

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue("public-websocket"),
                exchange = @Exchange(name = "topic-publish", type = ExchangeTypes.TOPIC),
                key = "public.websocket"
        ))
    @RabbitHandler
    public void process(String message) {
        System.out.println(message);
        sentMessage(message);
    }



    @OnError
    public void onError(Session session, Throwable error) {

    }

    public void sentMessage(String msg){
        if(sessions.size() != 0){
            sessions.stream().forEach(session1 -> {if(session1 != null) session1.getAsyncRemote().sendText(msg);});
        }
    }
}
