package com.lock.interfaces;

/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/28
 * @since 1.0
 */
public interface ZLock {



    Boolean tryLock();

    void releaseLock();


}
