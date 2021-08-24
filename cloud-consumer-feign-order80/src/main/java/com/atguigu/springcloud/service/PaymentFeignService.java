package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value="CLOUD-PAYMENT-SERVICE")// value告诉我是去调用注册中心的哪个微服务，微服务名
public interface PaymentFeignService {
    // 声明和 8001 8002支付服务的 controller里面一模一样， getMapping都一样
    @GetMapping("/payment/get/{id}") // 去上面feignClient注解是声明的微服务名中的微服务调用下面这个地址 ，应该可以 直接 调用service的
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout();
}
