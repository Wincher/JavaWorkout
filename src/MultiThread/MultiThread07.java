package MultiThread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wincher on 24/08/2017.
 * notify只是唤醒一个锁的其他线程不ing不能立即执行,我们可以将synchronized放到for循环里这样减小同步的粒度。
 * CountDownLatch
 */
public class MultiThread07 {
    /** volatile */
    private volatile static List list = new ArrayList();

    public void add() {
        list.add("wincher");
    }
    public int size(){
        return list.size();
    }

    /**
     * 这个方法用volatile变量，t2一直观察变量的值，知道期待的值出现停止
     * @param args
     */
    public static void main1(String[] args) {
         MultiThread07 m = new MultiThread07();
         Thread t1 = new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     for (int i = 0; i < 10; i++) {
                         m.add();
                         System.out.println("CurrentThread: " + Thread.currentThread().getName() + " add one element..");
                         Thread.sleep(500);
                     }
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }, "t1");
         Thread t2 = new Thread(new Runnable() {
             @Override
             public void run() {
                 while (true) {
                     if (m.size() == 5) {
                         System.out.println("CurrentThread: " + Thread.currentThread().getName() + " list size = 5 Thread stop..");
                         throw new RuntimeException();
                     }
                 }
             }
         }, "t2");
         t1.start();
         t2.start();
    }
    /**
     * 这个方法使用wait／notify，相比main1性能提高，t2方法不需要一直观察变量值
     * @param args
     */
    public static void main(String[] args) {
        MultiThread07 m = new MultiThread07();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //实例化出一个lock，当使用wait 和notify的时候，一定要配合synchroinzed关键字去使用
        //Object lock = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //synchronized (lock) {
                        for (int i = 0; i < 10; i++) {
                            m.add();
                            System.out.println("CurrentThread: " + Thread.currentThread().getName() + " add one element..");
                            Thread.sleep(500);
                            if (m.size() == 5) {
                                System.out.println("send notification..");
                                countDownLatch.countDown();
                                //lock.notify();
                            }
                        }
                    //}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //synchronized (lock) {
                    if (m.size() != 5) {
                        try {
                            System.out.println("enter t2..");
                            //lock.wait();
                            countDownLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("CurrentThread: " + Thread.currentThread().getName() + " list size = 5 Thread stop..");
                    throw new RuntimeException();
                //}
            }
        }, "t2");
        t2.start();
        t1.start();
    }
}
