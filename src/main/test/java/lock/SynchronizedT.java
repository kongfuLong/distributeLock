package lock;

/**
 * @author <a href="mailto:ruancl@59store.com">软软</a>
 * @version 1.0 16/5/29
 * @since 1.0
 */
public class SynchronizedT {

    private Integer count = 1;

    public void test(){

        for(int i=0;i<20;i++){
           new Thread() {
               @Override
               public void run() {
                   for(int j = 0;j<10;j++){
                       try {
                           Thread.sleep(200);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

add();
                   }
               }
           }.start();
        }

    }

    public void add(){
        synchronized (count){

            count++;
            System.out.println("线程当"+Thread.currentThread().getName()+"前基数:"+count);
        }
    }

}
