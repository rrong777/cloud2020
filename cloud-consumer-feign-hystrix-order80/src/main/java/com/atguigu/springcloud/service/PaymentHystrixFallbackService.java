package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "ok触发解耦的统一服务降级！";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "timeOut触发解耦的统一服务降级";
    }
}
