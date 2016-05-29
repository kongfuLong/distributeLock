import com.lock.imps.DistributeLock;
import com.lock.enums.LockServerEnum;
import com.lock.interfaces.ZLock;


import java.util.Date;

/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/29
 * @since 1.0
 */
public class TestMain  {

    private static Integer count = 0;





    public static void main(String[] args) {






        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    ZLock serviceLock = new DistributeLock("127.0.0.1:2181",LockServerEnum.TEST);

                    for (int j = 0; j < 1; j++) {


                        try {
                            if(!serviceLock.tryLock()){
                                System.out.println(Thread.currentThread().getName()+":锁等待失败,放弃锁");
                                continue;
                            }
                            Thread.sleep(6000);
                                System.out.println(Thread.currentThread().getName()+"任务执行成功:---------------------------"+new Date());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            serviceLock.releaseLock();
                        }

                    }
                }
            }.start();

        }
    }
}
