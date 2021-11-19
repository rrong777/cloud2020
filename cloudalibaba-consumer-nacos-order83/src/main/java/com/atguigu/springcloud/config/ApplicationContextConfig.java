package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationContextConfig
{
    @Bean
    @LoadBalanced // restTemplate结合ribbon去做负载均衡的时候一定要加这个
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}


