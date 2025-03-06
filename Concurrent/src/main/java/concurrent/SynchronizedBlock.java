package concurrent;

/**
 * @author wincher
 * @date 24/08/2017
 * 线程安全概念：当多个线程访问某一个类 (对象或方法),这个对象始终都能表现出正确的行为，那么这个类(对象或方法)就是线程安全的。
 * synchronized：可以在任意对象及方法上加锁，而加锁的这段代码成为"互斥区"或"临界区"
 */
public class SynchronizedBlock extends Thread {

    public static int count = 100;

    /**
     * 方法加synchronized或者为代码内加synchronized{}代码块,可保证线程安全
     */
    @Override
    public void run(){
        synchronized(this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    /**
     * 分析：放多个线程访问MultiThread01的run方法时，以排队的方式进行处理（这里排队是按照CPU分配的先后顺序而定的），
     * 一个线程想要执行synchronized修饰的方法里的代码：
     * 1.尝试获得锁
     * 2.如果拿到锁，执行synchronized代码体的内容：拿不到锁，这个线程就会不断的尝试获得这把锁，
     * 直到拿到为止，而且是多个线程同时去竞争这把锁。（也就是会有锁竞争的问题）
     */
    public static void main(String[] args) {
        SynchronizedBlock m = new SynchronizedBlock();
        int n = count;
        for (int i = 0; i < n; i++) new Thread(m,"t"+i).start();
    }

}
