package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)  {
        /**
         *下面这个对象相当于 yml中的
         *         spring:
         *           cloud:
         *              gateway:
         *                  routes:
         */
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
//         r->r.path("/guoji") 访问网关的这段uri 可以映射 .uri("http://news.baidu.com/guoji") 后面这个地址
        // .build() 构建route
        // 第二个参数是java.util.Function,四大函数，应用型的函数，有输入和输出，做了一个映射规则，
        routes.route("path_route_atguigu",r->r.path("/guoji")
                .uri("http://news.baidu.com/guoji")).build(); // 这个相当于 - id
        routes.route("path_route_atguigu2",r->r.path("/guonei")
                .uri("http://news.baidu.com/guonei")).build();
        //  构建routes
        return routes.build();
    }
}
