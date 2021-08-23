package com.atguigu.springcloud.lb;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {
        /**
         * 多线程环境牵扯到了多个线程去操作主物理内存里面的变量。改回来，先变量副本拷贝，本地改完了 要写回去。
         * 经常会发生线程写丢失，写值覆盖这样的问题
         * 三个现场操作主物理内存内存中的变量a=1 三个线程都 把a=1拷贝到自己本地，线程1现在想把 a=1修改成a=3，那这时候线程1
         * 遇到的问题就是他不期望有人在他之前动过a变量，如果有人动过a变量，线程1必须把a变量最新的值再取出来再修改。
         */
        AtomicInteger atomicInteger = new AtomicInteger(5);// main线程进来，拿到的就是5

//        main do somethis。。。 中间假设耗费了三秒钟，我希望三秒钟 atomicInteger在主物理内存中还是5.

        // 从主物理内存中拿到的就是5，拷贝到main线程的工作内存中还是5.main线程希望把主物理内存的值，从5改到2019
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data:" + atomicInteger.get());
        // 我现在期望你就是5，没人动过，比如三秒前，就是第一行代码，我从主物理内存中拿到的时候是5，三秒后，我现在第二行代码执行，
        // 我想往主物理内存中写，我希望主物理内存中的这个变量还是5这样证明没人动过， 如果compareAndSet方法的这个期望值，和我
        // 自身的值是一样的 说明这个值是没人动过，我就将主物理内存中的值修改为2019。这个方法返回值是一个bool 是否成功修改。

        // 假设我下面这个是另一个线程，和上面这行代码是 并发的 ，上面这行代码先去主物理内存 中 拿到atomicInteger然后比较交换2019
        //  下面这行去 比较交换的时候发现被改过了，就不会交换了 ，  返回false
        System.out.println(atomicInteger.compareAndSet(5, 1034) + "\t current data:" + atomicInteger.get());

        // new的实例对象，是在堆里面，堆在内存里面，
//        atomicInteger.getAndIncrement()
    }
}
