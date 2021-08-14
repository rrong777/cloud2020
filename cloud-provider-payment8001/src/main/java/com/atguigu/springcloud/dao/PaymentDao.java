package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// 一般dao类都是一个接口
@Mapper // 如果使用mybatis 推荐使用这个注解
public interface PaymentDao {
    // 新增 要嘛是 save 要嘛是add 要嘛是create
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") long id);
}
