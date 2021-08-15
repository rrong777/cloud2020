package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * RestTemplate 对HttpClient做了一次封装
 */
@RestController
@Slf4j
@RequestMapping("/consumer")
// 模拟浏览器往后台下订单 发起一个rest请求
public class OrderController {
//    单机环境下 这样写没问题，但是现在请求的是集群服务
//    public static final String PAYMENT_URL = "http://localhost:8001";
    // ip:port使用服务名代替
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;

    // 客户端浏览器发起请求调用这个，再向payment服务发起请求调用，发送的都是get请求。
    @GetMapping("/payment/create")
    public CommonResult<Payment> create(Payment payment)  {
        // 三个参数 url paramter 返回值类型 发起一个post请求
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }
    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id)  {
        // 发起一个get请求
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
}
