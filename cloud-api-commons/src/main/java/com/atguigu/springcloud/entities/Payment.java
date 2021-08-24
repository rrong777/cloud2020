package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Data
@AllArgsConstructor // 全参
//@NoArgsConstructor// 无参构造器
// Serializable 序列化 后续分布式部署用得到
public class Payment implements Serializable {
    private Long id;
    private String serial;

    public Payment() {
    }
}
