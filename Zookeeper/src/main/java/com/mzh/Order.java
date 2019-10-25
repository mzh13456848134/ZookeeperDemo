package com.mzh;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Oreder
 * 创建人：mzh
 * 创建时间： 2019/10/25 14:09
 */
public class Order {

    //产生一个订单号码
    private static int orderNumber = 1;

    //获取订单号码
    public int getOrderNumber(){
        return orderNumber++;
    }

}
