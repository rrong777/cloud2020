package com.atguigu.springcloud;

import lombok.Data;
import lombok.Getter;

@Data
public class Test {
    private Integer id;

    public Test(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return !this.id.equals(((Test)obj).getId());
    }

    public static void main(String[] args) {
        Test t1 = new Test(1);
        Test t2 = new Test(1);
        System.out.println(t1 == t2);
        System.out.println(t1.equals(t2));
    }
}
