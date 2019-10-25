package com.mzh;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * ZkDistributedLock
 * 创建人：mzh
 * 创建时间： 2019/10/25 16:20
 */
public class ZkDistributedLock extends ZkAbstractLock {
    @Override
    public void zkWait() {
        //1.创建zookeeper数据监听器
        IZkDataListener zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                //2.当监控到节点被删除了，放一个进程进入。
                // 用于处理节点删除的业务逻辑
                if(countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        };


        //3.zookeeper客户端加入了该节点的监控
        zkClient.subscribeDataChanges(PATH,zkDataListener);
        //4.如果节点存在，直接阻塞进来的进程
        if(zkClient.exists(PATH)){
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean tryLock() {
        try {
            //直接创建节点，争抢到锁直接创建，如果存在就直接产生异常
            zkClient.createEphemeral(PATH);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
