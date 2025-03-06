package concurrent;

/**
 * @author wincher
 * @date 24/08/2017
 * synchronized锁是可重入的:
 */
public class SynchronizedReentrant {
    public synchronized void method1(){
        System.out.println("method1..");
        method2();
    }
    public synchronized void method2(){
        System.out.println("method2..");
        method3();
    }
    public synchronized void method3(){
        System.out.println("method3..");
    }

    public static void main(String[] args) {
        SynchronizedReentrant m = new SynchronizedReentrant();
        Thread t1 = new Thread(() -> m.method1());
        t1.start();
    }
}
