package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//  负载均衡算法实现
// 注册成容器中的组件
@Component
public class MyLB implements LoadBalancer {
    // 原子整型
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 这个方法很重要 final 不允许修改
     * 得到一个下表，然后再自增，下一次访问服务的时候还要根据这个下标返回
     *         for(;;) 这个是自旋锁
     * @return
     */
    public final int getAndIncrementIndex() {
        int current;
        int next;
        // 就是利用AtomicInteger的自旋锁（cas）将next和current 对比设置之后返回
        do{
            // 当前是第几次通过ribbon负载均衡发起的请求
            current = this.atomicInteger.get();
            // 下一次是第几次通过ribbon负载均衡发起的请求
            next = current >= 2147483647 ? 0 : current + 1; // 2147483647 Integer.MAX_VALUE Integer的最大取值
            // 上面只是从主物理内存中 取出atomicInteger的值，下面是比较当前
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("**********第几次访问，次数：next:" + next);
        return next;
    }
//    模仿IRule接口下的RoundRobinRule写一个轮询负载均衡算法
    @Override
    public ServiceInstance instance(List<ServiceInstance> instanceList) {
        int index = getAndIncrementIndex() % instanceList.size();
        return instanceList.get(index);
    }
}
