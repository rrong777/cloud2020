package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j//lombook可以使用这个注解
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPost;

    // 服务发现客户端，我自己注册到eureka之后，我自己在注册 中心的一些基础信息可以通过这个客户端拿到
    @Resource
    private DiscoveryClient discoveryClient;

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
    @PostMapping("/createForPostForEntity")
    public CommonResult createForPostForEntity(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*******插入结果：" + result + "serverPost:" + serverPost);
        if(result  > 0) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping("/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果：" + payment);
        if(payment  != null) {
            return new CommonResult(200, "查询成功", payment + "serverPost:" + serverPost);
        } else {
            return new CommonResult(444, "没有id为：" + id + "的对应记录", null);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return "8001";
    }


    @GetMapping("/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();// 盘点一下我们在eureka中注册好的微服务有哪些
        // eureka中的服务列表清单
        for (String service : services) {
            log.info("*******service:" + service);
        }

        // getInstances 传入服务名称，获得这个服务注册在eureka中的实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return discoveryClient;
    }

    /**
     * 测试Feign超时调用 模拟业务中的长流程调用
     * @return
     */
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPost;
    }
}
