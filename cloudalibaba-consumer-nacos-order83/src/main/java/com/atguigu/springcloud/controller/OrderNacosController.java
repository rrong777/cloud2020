package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
@Slf4j
public class OrderNacosController
{

    @Resource
    private RestTemplate restTemplate;

//    以前要这样写 去调用 其他微服务.现在我们可以像下面这样，去配置文件中读取出来，不再写常量。配置和代码分离
//    public static final String SERVER_URL="http://服务名称";
    // 83服务也是注册到nacos的，可以直接用服务名去nacos拿到我要访问的节点的信息。服务发现
    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Long id)
    {
        return restTemplate.getForObject(serverURL+"/payment/nacos/"+id,String.class);
    }

}


