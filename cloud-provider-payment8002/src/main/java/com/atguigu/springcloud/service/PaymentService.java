package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    // service中很多接口和dao接口中的声明一致的，简单的增删改查
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") long id);
}
