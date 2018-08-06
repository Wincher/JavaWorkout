package multithread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author  wincher
 * @date 24/08/2017
 * static volatile变量只具有可见性，并不能保证原子性，因为如果两个线程同时读取共享内存的值然后更改操作，那么写回的时候就会覆盖掉一个值
 * AtomicX可以保证在多线程的原子性（注意atomic类只保证本身方法原子性，并不保证多次操作的原子性，也就是说如果多个
 * atomic操作在非同步代码块或方法中那么是不能够保证不被其他的线程中断的）
 */
public class MultiThread0601 extends Thread {
    private static volatile int count;
    /**
     * 线程同步的int封装类
     */
    private static AtomicInteger aCount = new AtomicInteger(0);
    private static void addCount() {
        int totalTimes = 10000;
        for (int i = 0; i < totalTimes; i++) {
            count++;
            aCount.incrementAndGet(); //++
        }

        System.out.println("count:" + count + "   " + "aCount" + aCount);
    }
    @Override
    public void run() {
        addCount();
    }

    public static void main(String[] args) {
        int totalTimes = 10;
        MultiThread0601[] arr = new MultiThread0601[totalTimes];
        for (int i = 0; i < totalTimes; i++) {
            arr[i] = new MultiThread0601();
        }
        for (int i = 0; i < totalTimes; i++) {
            arr[i].start();
        }
    }
}
