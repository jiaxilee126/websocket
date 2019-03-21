# websocket

本项目主要集成websocket、rabbitMQ和springboot做一个消息推送系统；

传统的消息是使用ajax做轮询：占带宽，每次ajax请求会有大量的请求头，可能消息内容很少

websocket优势：服务器与客户端之间的标头信息很少，服务器可以直接推送消息给客户端。


主要的架构如下：





