# distributeLock
使用zookeeper实现的分布式锁，疑惑待解，每个锁对象生成都需要进行zookeeper连接，连接销量高吗？对比连接一次永久会话又如何？



本锁针对业务场景设置，enum里面对应不同的业务类型，同时执行的任务数量   该业务锁的超时时间都在enum对象设置