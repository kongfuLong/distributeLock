/*
import com.lock.core.LockFactory;
import com.lock.core.ServiceLock;
import com.lock.enums.LockServerEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

*/
/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/29
 * @since 1.0
 *//*

public class JuniTest {

    @Autowired
    private LockFactory lockFactory;

    private Integer count = 0;

    @Test
    public void testLock() {
        ServiceLock serviceLock = lockFactory.getLockServe(LockServerEnum.TEST);
        for (int i = 0; i < 20; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String id = serviceLock.lock();
                        if (id == null) {
                            System.out.println(Thread.currentThread().getName() + ":锁等待失败,放弃锁");
                            return;
                        }
                        count++;
                        System.out.println(Thread.currentThread().getName() + ":" + count);
                        serviceLock.unlock(id);
                    }
                }
            }.start();

        }
    }
}
*/
