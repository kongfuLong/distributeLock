package com.lock.imps;

import com.lock.enums.LockServerEnum;
import com.lock.interfaces.ZLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/29
 * @since 1.0
 */
public class DistributeLock implements ZLock{


    private ZooKeeper zooKeeper;

    private String lockNode;

    private Integer lockTimeOut;

    private String ROOTNODE = "/distributeLock_";

    private Integer lockNumber;

    private String currentNode;

    private final int sessionTimeOut = 60000;

    private Boolean lock = false;

    private CountDownLatch connect = new CountDownLatch(1);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributeLock(String zkUrl,LockServerEnum lockServerEnum) {
        this.lockNode = lockServerEnum.getLockNode();
        this.lockTimeOut = lockServerEnum.getTimeOut();
        this.lockNumber = lockServerEnum.getNumber();
        this.ROOTNODE = ROOTNODE+lockNode;
        try {
            zooKeeper = new ZooKeeper(zkUrl, sessionTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(event.getState()== Event.KeeperState.SyncConnected){
                        connect.countDown();//连接成功 连接阻塞取消
                    }
                }
            });
            connect.await();//阻塞到连接成功
            //判断节点状态 并进行监听
            Stat stat = zooKeeper.exists(ROOTNODE, false);
            if(stat == null){
                //跟节点不存在则建立一个永久的跟节点
                zooKeeper.create(ROOTNODE, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Boolean checkNodeSort(){
        int idIndex = currentNode.lastIndexOf("_")+1;
        String myNumberOrigin = currentNode.substring(idIndex);
        Integer myNumber = Integer.parseInt(myNumberOrigin);
        //获取当前节点的序号  和所有节点进行比较
        List<String> nodes = null;
        try {
            nodes = zooKeeper.getChildren(ROOTNODE, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("监听到时间变化"+event);
                    if(event.getType()== Event.EventType.NodeChildrenChanged && checkNodeSort()){
                        lock = true;
                        countDownLatch.countDown();//唤醒阻塞中的线程继续执行
                    }
                }
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SortedSet<Integer> sortedSet = new TreeSet<>();
        for (String node : nodes){
            sortedSet.add(Integer.parseInt(node.substring(node.lastIndexOf("_")+1)));
        }
        Integer temp = lockNumber;
        while (sortedSet.size()>0 && temp>0){
            temp -- ;
            int first = sortedSet.first();
            if(first >= myNumber){
                return true;
            }
            sortedSet.remove(first);
        }
        return false;
    }


    public Boolean tryLock(){
        System.out.println(Thread.currentThread().getName()+"线程请求加锁中"+new Date());
        //使用zk事务锁
        //使用临时 顺序节点创建
        String path = String.format("%s/%s_",ROOTNODE,lockNode);
        try {
            currentNode =  zooKeeper.create(path,new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            if(checkNodeSort()){
                System.out.println("抢锁成功!");
                lock = true;
            }else{
                countDownLatch.await(lockTimeOut, TimeUnit.SECONDS);
                System.out.println("锁状态"+lock+"------"+new Date());
            }
            return lock;


        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lock;

    }


    public void releaseLock(){
        System.out.println("释放锁~~~~"+new Date());
        try {
            zooKeeper.delete(currentNode,-1);
            //zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
