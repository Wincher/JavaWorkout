package concurrent;

/**
 * @author  wincher
 * @date 24/08/2017
 */
public class SyncMethodLockObjOrClazz01 {

    public void method1() {
        try {
            //同步代码块(this为锁)和方法synchronized效果一致,
            // 去掉或替换为this.getClass()(method2持有的锁不一致),则不构成竞争
            synchronized (this.getClass()) {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName());
    }
    public static void main(String[] args) throws InterruptedException {

        final SyncMethodLockObjOrClazz01 m = new SyncMethodLockObjOrClazz01();

//      线程先持有m对象的Lock锁，其他线程如果在这个时候调用对象中的同步(synchronized)方法则需等待，也就是同步
        Thread t1 = new Thread(() -> m.method1(),"t1");
        Thread t2 = new Thread(() -> m.method2(),"t2:");

        t1.start();
        t2.start();
    }
}
