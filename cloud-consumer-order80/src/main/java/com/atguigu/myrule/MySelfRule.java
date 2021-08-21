package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    //  提供一个随机的负载策略覆盖轮询。
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
