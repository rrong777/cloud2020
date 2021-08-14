package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 全参
@NoArgsConstructor// 无参构造器
//  泛型 这个类是通用的。如果泛型是Order 返回的就是Order 如果泛型是Payment 返回的就是payment
public class CommonResult<T> {
    // 最常见的 异常
    // 404 not_found 第一个参数code 第二个参数异常信息
    private Integer code;
    private String message;
    private T data;

    // 如果data是null 定义一个两个参数的构造器
    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
