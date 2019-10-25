package com.mzh;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * ZkImpl
 * 创建人：mzh
 * 创建时间： 2019/10/25 15:04
 */
public abstract class ZkAbstractLock implements  ZkLock {
    //zookeeper服务器地址
    public static final String SERVER = "192.168.177.177:2181";
    //zookeeper连接超时时间
    public static final int TIME_OUT = 45 * 1000;
    //zookeeper客户端
    ZkClient zkClient = new ZkClient(SERVER,TIME_OUT);
    //需要创建的节点名称
    public static final String PATH = "/myzookeeperNode";
    //用来阻塞线程的
    protected CountDownLatch countDownLatch = null;
    @Override
    public void lock() {
        //1.尝试争抢
        if(tryLock()){
            //2.争抢到锁之后
            System.out.println(Thread.currentThread().getName() + "\t争抢锁成功！！");
        }else{
            //3.争抢不到阻塞
            zkWait();
            //4.唤醒之后重新再回到1
            lock();
        }
    }

    //具体如何实现等待，让最上层继承者去实现
    public abstract void zkWait();

    //具体如何上锁，让最上层继承者去实现
    public abstract boolean tryLock();

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + "\t释放锁成功！！");
        System.out.println();
        System.out.println();
        if(zkClient != null){
            //因为创建的是临时节点，当执行quit退出，临时节点就删除了，相当于可与创建了
            zkClient.close();
        }
    }
}
