package concurrent;

/**
 * @author wincher
 * @date 24/08/2017
 * <p>
 * 关键字synchronized取得的锁都是对象锁，而不是把一段代码(方法)当作锁，
 * 所以代码中哪个线程先执行synchronized关键字的方法，哪个线程就持有该方法所属对象的锁(Lock)，
 * <p>
 * 在静态方法上加synchronized关键字，表示锁定.class类，类级别的锁(独占.class类).
 * printNum去掉static依然可以保证线程同步,即验证了上面一行所说的理论.
 */
public class SyncMethodLockObjOrClazz02 {

    public synchronized void printNum() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void staticPrintNum() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncMethodLockObjOrClazz02 example = new SyncMethodLockObjOrClazz02();

        Thread t1 = new Thread(() -> {
            example.printNum();
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            example.printNum(); //替换为example.staticPrintNum();测试静态锁
        }, "Thread-2");

        t1.start();
        t2.start();
    }

}
