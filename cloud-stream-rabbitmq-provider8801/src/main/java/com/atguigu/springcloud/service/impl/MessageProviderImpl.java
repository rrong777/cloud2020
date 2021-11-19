package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wql78
 * @title: MessageProviderImpl
 * @description: @TODO
 * @date 2021-11-19 16:19:04
 */
@EnableBinding(Source.class) // 定义消息的推送管道 1. 开启输出源的通道，
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;// 消息发送管道，2. 通过这个output channel
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        // 3. 往消息中间件发送流水号
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("******serial:" + serial);
        return null;
    }
}
