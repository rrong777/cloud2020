package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;
@Service
public class PaymentService {
    //成功
    public String paymentInfo_OK(Integer id){
//        Hystrix其实用的是tomcat里面的线程池
        // 这里不会报错，这句话就是返回当前处理的线程的名字，再返回我们的id
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_OK,id：  "+id+"\t"+"哈哈哈"  ;
    }

    //失败 模拟出错，超时是会导致服务降级的一种情况
//    fallbackMethod 如果我现在出事了，谁替我兜底，服务降级
    // paymentInfo_TimeOutHandler 就是在下面的声明，方法头除方法名，其他保持一致，paymentInfo_TimeOut出了问题了，过来找下面这个，paymentInfo_TimeOutHandler替你兜底，
    // HystrixProperty 配置你这个线程的超时时间是3秒钟，你自己处理超过三秒钟就调用paymentInfo_TimeOutHandler 做服务降级
    // 设置自身调用超时时间的峰值，超过了3秒就是超时出错，需要有一个兜底方法就是paymentInfo_TimeOutHandler
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")  //3秒钟以内就是正常的业务逻辑
    })
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 3;
//        int age = 10 / 0;
        // 模拟程序处理长业务，程序自身是没错，但是就是要这么长时间，
        try { TimeUnit.SECONDS.sleep(timeNumber); }catch (Exception e) {e.printStackTrace();}
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_TimeOut,id：  "+id+"\t"+"呜呜呜"+" 耗时(秒)" + timeNumber;
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   系统繁忙，请稍后重试！,id：  "+id+"\t"+"/(ㄒoㄒ)/~~";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
    }) // 上面四个配置的意思是，假设在10000ms内，十次请求有60%的 概率是失败的之后就打开保险丝（断路器）触发熔断
    // 上面就是一个微服务接口
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }
    // 下面就是一个服务降级的兜底方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }

}
