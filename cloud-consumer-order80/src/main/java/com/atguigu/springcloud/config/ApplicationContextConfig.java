package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    // 往容器注入restTemplate
    // @Bean标签用注解的方式实现 applicationContext.xml <bean id="" class="">
    // 容器注入restTemplate
    @Bean
    // 提供自定义负载均衡算法 去掉这个注解 去掉这个注解后，如果负载均衡算法生效，就一定是我们自己的。
//    @LoadBalanced // 以前你给我rest template一个url我直接请求就好了， 现在你给我的是服务名称，不再写死的 url你要给我一个负载均衡机制，我才
    // 知道多个节点下去请求哪个节点 。 默认应该就是轮询
    // loadBalance 加载平衡，平均。就是分发呗
    // restTemplate的负载平衡，这个就是ribbon负责的负载均衡。提前说一嘴
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
