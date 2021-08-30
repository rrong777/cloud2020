package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 自定义网关filter
 * 加上这个过滤器之后
 * http://localhost:9527/payment/lb?uname=13 请求必须带参数uname 不然不让你过去
 */
@Component
@Slf4j
public class MyGatewayLogFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***************************com in MyGatewayLogFilter: " + new Date());
//        我要求你请求url拼接的参数上带着uname
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if(uname == null)  {
            log.info("*****************非法用户");
            // 不被接受的请求
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete(); // 完成本次请求
        }
        //  放行
        return chain.filter(exchange);
    }
    // 下面这个方法返回的是加载过滤器的顺序，数字越小顺序越高
    @Override
    public int getOrder() {
        return 0;
    }
}
