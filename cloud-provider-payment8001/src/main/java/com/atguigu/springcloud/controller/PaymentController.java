package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/payment")
@Slf4j//lombook可以使用这个注解
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    // 前后端分离了，传给前端的只能是个CommonResult。这个 也是约定规范之一，传给他不涉及具体的业务
    @PostMapping("/create")
    // post请求是可以用形参入仓直接去接收url中的参数的，get请求也可以，但是如果是在请求body中的参数要映射到形参入参，需要加@RequestBody
//    public CommonResult create(@RequestBody Payment payment){
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("*******插入结果：" + result);
        if(result  > 0) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            // 假设我们约定444就是插入数据库失败
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果：" + payment);
        if(payment  != null) {
            return new CommonResult(200, "查询成功", payment);
        } else {
            // 假设我们约定444就是插入数据库失败
            return new CommonResult(444, "没有id为：" + id + "的对应记录", null);
        }
    }
}
