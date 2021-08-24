package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

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
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber = 3;
        // 模拟程序处理长业务，程序自身是没错，但是就是要这么长时间，
        try { TimeUnit.SECONDS.sleep(timeNumber); }catch (Exception e) {e.printStackTrace();}
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_TimeOut,id：  "+id+"\t"+"呜呜呜"+" 耗时(秒)"+timeNumber;
    }

}
