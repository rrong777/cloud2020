package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/payment/get/{id}") // 去上面feignClient注解是声明的微服务名中的微服务调用下面这个地址 ，应该可以 直接 调用service的
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)  {
        // 80客户端找的 是自己的paymentFeignService，只不过这个service是一个feign调用，这个service是一个FeignClient
        // 回去eureka上面找 叫做 CLOUD-PAYMENT-SERVICE的这个服务，下面这个方法调用的就是 CLOUD-PAYMENT-SERVICE 这个服务的
        // /payment/get/{id} 这个接口
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        // openfeign-ribbon 客户端一般默认等待1秒钟，
        return paymentFeignService.paymentFeignTimeout();
    }
}
