package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/consumer")
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod") // 声明全局兜底方法
public class OrederHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("/payment/hystrix/ok/{id}")
//    @HystrixCommand // 声明全局兜底方法之后要加上这个注解，对应的方法才会触发hystrix服务降级，你没声明这个注解，那方法不会触发服务降级
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
//        int a= 10 /0; 现在声明的是feign接口的服务降级，你不能 自己先出错 了，还没 去调用feign接口就不会触发feign接口的服务降级
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }



    @GetMapping("/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")  //3秒钟以内就是正常的业务逻辑
//    }) // 声明自定义兜底方法
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    //兜底方法
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
    }


    //全局统一的 兜底方法
    public String paymentGlobalFallbackMethod(){// 请记住 全局统一的兜底方法必须是无参的
        return "全局统一fallback(┬＿┬)";
    }


}
