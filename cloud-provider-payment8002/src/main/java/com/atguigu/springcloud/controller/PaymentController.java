package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPost;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*******插入结果：" + result + "serverPost:" + serverPost);
        if(result  > 0) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }
    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return "8002";
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果：" + payment);
        if(payment  != null) {
            // 这里返回值变成 'Payment(id=31, serial=尚硅谷001)serverPost:8002' String feign调用无法映射成CommonResult<Payment> 主要是Payment无法映射成对象
//            return new CommonResult(200, "查询成功", payment + "serverPost:" + serverPost);
            return new CommonResult(200, "查询成功，serverPost" + serverPost, payment);
        } else {
            return new CommonResult(444, "没有id为：" + id + "的对应记录", null);
        }
    }
}
