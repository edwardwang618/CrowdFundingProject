package com.atguigu.zk.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ZkTest {
    
    private ZooKeeper zooKeeper;
    
    {
        String connectString = "127.0.0.1:2181";
        int sessionTimeout = 5000;
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            
            }
        };
        
        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testUpdateNodeData() throws KeeperException, InterruptedException {
        String path = "/animal/cat";
        byte[] resultByteArray = zooKeeper.getData(path, false, new Stat());
        
        String result = new String(resultByteArray);
        System.out.println("old value = " + result);
        
        byte[] newValueByteArray = "miaomiao".getBytes();
        int version = -1;
        Stat stat = zooKeeper.setData(path, newValueByteArray, version);

        int newVersion = stat.getVersion();

        System.out.println("newVersion = " + newVersion);

        resultByteArray = zooKeeper.getData(path, false, new Stat());
        result = new String(resultByteArray);
        
        System.out.println("new value = " + result);
    }
    
    @Test
    public void testNoticeOnce() throws KeeperException, InterruptedException {
        String path = "/animal/cat";
        Watcher watcher = watchedEvent -> System.err.println("接收到了通知，值发生了修改");
    
        byte[] oldValue = zooKeeper.getData(path, watcher, new Stat());
    
        System.out.println("oldValue = " + new String(oldValue));
        
        while (true) {
            Thread.sleep(5000);
            System.err.println("当前方法原本要执行的业务逻辑");
        }
    }
    
    @Test
    public void testNoticeForever() throws KeeperException, InterruptedException {
        String path = "/animal/cat";
        getDataWithNotice(zooKeeper, path);
        while (true) {
            Thread.sleep(5000);
            System.err.println("当前方法原本要执行的业务逻辑" + Thread.currentThread().getName());
        }
    }
    
    public void getDataWithNotice(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        byte[] resultByteArray = zooKeeper.getData(path, watchedEvent -> {
            try {
                getDataWithNotice(zooKeeper, path);
                System.err.println("通知线程的名称：" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, new Stat());
    
        String result = new String(resultByteArray);
        System.err.println("当前节点值：" + result);
    }
}
