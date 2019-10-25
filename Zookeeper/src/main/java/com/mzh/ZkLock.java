package com.mzh;

/**
 * zkLock
 * 创建人：mzh
 * 创建时间： 2019/10/25 15:03
 */
public interface ZkLock {
    public void lock();
    public void unlock();
}
