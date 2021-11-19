package springcloud.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author wql78
 * @title: ReceiveMessageListenerController
 * @description: @TODO
 * @date 2021-11-19 17:20:30
 */
@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListenerController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT) // 监听的是输入源
    public void input(Message<String> message) {
        // 发送的时候 output.send(MessageBuilder.withPayload(serial).build()); 是build一个Message对象，接收的时候也应该是一个Message对象
        System.out.println("消费者2号，------------->接收到的消息" + message.getPayload() + "\tport:" + serverPort);
    }
}
