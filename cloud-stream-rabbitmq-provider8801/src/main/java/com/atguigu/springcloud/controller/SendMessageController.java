package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wql78
 * @title: SendMessageController
 * @description: @TODO
 * @date 2021-11-19 16:24:15
 */
@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;
    @GetMapping("/sendMessage")
    public String sendMessage() {
        // 每调用一次，就发送一次流水号
        return messageProvider.send();
    }
}
