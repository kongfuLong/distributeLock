package com.lock.enums;

/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/28
 * @since 1.0
 */
public enum  LockServerEnum {

    TEST("test",10,2);

    private String lockNode;
    private Integer timeOut;//锁等待时间  单位/s
    private Integer number;

    LockServerEnum(String lockNode, Integer timeOut, Integer number) {
        this.lockNode = lockNode;
        this.timeOut = timeOut;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLockNode() {
        return lockNode;
    }

    public void setLockNode(String lockNode) {
        this.lockNode = lockNode;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
