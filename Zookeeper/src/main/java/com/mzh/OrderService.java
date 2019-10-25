package com.mzh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OrderService
 * 创建人：mzh
 * 创建时间： 2019/10/25 14:11
 */
public class OrderService {
    //资源类
    Order order = new Order();

    private ZkLock zkLock = new ZkDistributedLock();
    //线程操作资源类
    public void getOderName() {
        zkLock.lock();
        try {
            System.out.println("当前订单号：" + order.getOrderNumber());
        } finally {
            zkLock.unlock();
        }
    }
    //以下是模拟分布式的多线程测试
    public static void main(String[] args) {
        System.out.println("------------");
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                new OrderService().getOderName();
            },String.valueOf(i)).start();
        }
    }



    //以下是非分布式的线程测试
    /*private Lock lock = new ReentrantLock();

    //线程操作资源类
    public void getOderName() {
        lock.lock();
        try {
            System.out.println("当前订单号：" + order.getOrderNumber());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        System.out.println("------------");
        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                orderService.getOderName();
            }).start();
        }
    }*/
}
