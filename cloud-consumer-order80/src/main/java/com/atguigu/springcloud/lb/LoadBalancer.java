package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface LoadBalancer {


    // 容器加载的时候就告诉我有多少服务实例，供我负载均衡
    // instanceList目前某个 服务有多少个节点
    ServiceInstance instance(List<ServiceInstance> instanceList);

}
