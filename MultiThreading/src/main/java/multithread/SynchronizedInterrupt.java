package multithread;

//import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized关键字和ReentrantLock类都是用于实现线程同步的机制，但它们在中断处理方面存在一些差异。
 */
public class SynchronizedInterrupt {

    /**
     * synchronized 关键字
     * 当线程在持有某个对象的锁时，如果另一个线程尝试中断持有锁的线程，会发生以下情况：
     * 抛出 InterruptedException：如果一个线程在调用 synchronized 同步代码块时被中断，它将抛出 InterruptedException。
     * 这通常发生在调用 wait(), wait(long timeout), sleep(long millis), 或 join() 等方法时。
     * 不直接响应中断：如果线程仅仅在持有锁的 synchronized 代码块中执行，而没有调用上述可能抛出 InterruptedException 的方法，
     * 那么即使线程被中断，它也不会直接响应中断（即不会抛出 InterruptedException）。在这种情况下，中断状态会被设置，
     * 但线程将继续执行完同步代码块后才会注意到中断状态.
     */
    public static void main(String[] args) throws InterruptedException {
        //tips:输出结果看acquired lock后才interrupted(证明不响应中断),还是反过来(证明响应中断)
//        new SynchronizedInterrupt().testSynchronizedMethod();
        new SynchronizedInterrupt().testReentrantLock();
    }



    public void testReentrantLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable run = () -> {
            try {
//                由于我们使用了lockInterruptibly()，当Thread-2在等待锁或sleep()期间被中断时，会立即抛出InterruptedException，并执行相应的catch块。
//                如果将lockInterruptibly()替换为lock()则不会在等待锁的时候响应中断，只有在线程已经获取到锁，并且在执行sleep()方法时，才会抛出异常。
                lock.lockInterruptibly();
//                lock.lock();
                System.out.println(Thread.currentThread().getName() + " acquired lock.");
                Thread.sleep(5000); // 模拟长时间操作
                System.out.println(Thread.currentThread().getName() + " finished.");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted while waiting for lock or during sleep.");
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        };

        test(run);
    }

    private void test(Runnable run) throws InterruptedException {
        Thread t1 = new Thread(run, "Thread-1");
        t1.start();
        Thread.sleep(1000); // 确保t1先获取锁
        Thread t2 = new Thread(run, "Thread-2");
        t2.start();
        System.out.println("Thread-2 started and before try interrupt ");
        t2.interrupt();
        System.out.println("after Thread-2 try interrupt");
        System.out.println("Main thread : in " + t1.getName() + ": state = " + t1.getState());
        System.out.println("Main thread : in " + t2.getName() + ": state = " + t2.getState());
        t1.join();
        t2.join();
        System.out.println("Main thread finished.");
    }

}
