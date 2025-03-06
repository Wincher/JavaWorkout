package concurrent;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wincher
 * @date 24/08/2017
 * notify只是唤醒一个锁的其他线程不ing不能立即执行,我们可以将synchronized放到for循环里这样减小同步的粒度。
 * CountDownLatch
 */
public class VolatileVisibility {
    /** volatile */
    private volatile static List list = new ArrayList();

    public void add() {
        list.add("wincher");
    }
    public int size(){
        return list.size();
    }

    public static void volatileVisibilityUsingLock(String[] args) throws InterruptedException {
//        volatileVisibility();
        volatileVisibilityUsingLock();
    }

    /**
     * 这个方法用volatile变量，t2一直观察变量的值，知道期待的值出现停止
     */
    @SuppressWarnings(value = "unused")
    public static void volatileVisibility() throws InterruptedException {
         VolatileVisibility m = new VolatileVisibility();
         Thread t1 = new Thread(() -> {
             try {
                 for (int i = 0; i < 10; i++) {
                     m.add();
                     System.out.println("CurrentThread: " + Thread.currentThread().getName() + ": add one element and now size: " + m.size());
                     Thread.sleep(500);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }, "t1");
         Thread t2 = new Thread(() -> {
             while (true) {
                 if (m.size() == 5) {
                     System.out.println("CurrentThread: " + Thread.currentThread().getName() + ": list size = 5 Thread stop..");
//                     throw new RuntimeException();
                     break;
                 }
             }
         }, "t2");
         t1.start();
         t2.start();
         t1.join();
         t2.join();
    }
    /**
     * 这个方法使用wait／notify配合同步代码块，相比main1性能提高，t2方法不需要一直观察变量值
     * 或者使用CountDownLatch 不需要同步代码块,但并不一定在countdown后立马切换到监控线程.
     */
    @SuppressWarnings(value = "unused")
    public static void volatileVisibilityUsingLock() throws InterruptedException {
        VolatileVisibility m = new VolatileVisibility();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        //实例化出一个lock，当使用wait 和notify的时候，一定要配合synchronized关键字去使用
        //而使用CountDownLatch时,就不需要同步代码
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            try {
                synchronized (lock){
                    for (int i = 0; i < 10; i++) {
                        m.add();
                        System.out.println("CurrentThread: " + Thread.currentThread().getName() + ": add one element and now size: " + m.size());
                        Thread.sleep(500);
                        if (m.size() == 5) {
                            System.out.println("CurrentThread: " + Thread.currentThread().getName() + ": send notification..");
//                            countDownLatch.countDown();
                            lock.notify();
                            lock.wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
             synchronized (lock){
                if (m.size() != 5) {
                    try {
                        System.out.println("enter t2..");
                        lock.wait();
//                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("CurrentThread: " + Thread.currentThread().getName() + " list size = " + m.size() + " Thread stop..");
                lock.notify();
                throw new RuntimeException();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(countDownLatch.getCount());
    }
}
