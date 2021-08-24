package com.atguigu.springcloud.entities;

import lombok.Data;

import java.util.Optional;

@Data
public class Test {
    private String t1;
    private String t2;

    public static void main(String[] args) {
        Test test = new Test();
        test.setT1("1");
        test.setT2("2");
        Optional<Test> test1 = Optional.of(test);
        Test test2 = test1.orElse(null);
        test = null;
        Optional<Test> test3 = Optional.of(test);
        Test test4 = test3.orElse(null);


    }
}
