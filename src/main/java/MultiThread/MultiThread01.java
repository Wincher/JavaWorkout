package MultiThread;

/**
 * Created by wincher on 24/08/2017.
 * 线程安全概念：当多个线程访问某一个类 (对象或方法),这个对象始终都能表现出正确的行为，那么这个类(对象或方法)就是线程安全的。
 * synchronized：可以在任意对象及方法上加锁，而加锁的这段代码成为"互斥区"或"临界区"
 */
public class MultiThread01 extends Thread {
    private int count = 5;

    @Override
    //synchronized加锁
    public void run(){
        count--;
        System.out.println(this.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        /**
         * 分析：放多个线程访问MultiThread01的run方法时，以排队的方式进行处理（这里排队是按照CPU分配的先后顺序而定的），
         * 一个线程想要执行synchronized修饰的方法里的代码：
         * 1.尝试获得锁
         * 2.如果拿到锁，执行synchronized代码体的内容：拿不到锁，这个线程就会不断的尝试获得这把锁，
         * 直到拿到为止，而且是多个线程同时去竞争这把锁。（也就是会有锁竞争的问题）
         */
        MultiThread01 m = new MultiThread01();
        Thread t1 = new Thread(m,"t1");
        Thread t2 = new Thread(m,"t2");
        Thread t3 = new Thread(m,"t3");
        Thread t4 = new Thread(m,"t4");
        Thread t5 = new Thread(m,"t5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }

}
