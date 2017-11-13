package MultiThread;

/**
 * Created by wincher on 24/08/2017.
 */
public class MultiThread03 {
    public synchronized void method1() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**  synchronized */
    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName());
    }
    public static void main(String[] args) {

        final MultiThread03 m = new MultiThread03();

        /**
         *  分析：
         *  t1线程先持有m对象的Lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
         *  t1线程先持有m对象的Lock锁，t2线程如果在这个时候调用对象中的同步(synchronized)方法则需等待，也就是同步
         */
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.method1();;
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.method2();;
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
