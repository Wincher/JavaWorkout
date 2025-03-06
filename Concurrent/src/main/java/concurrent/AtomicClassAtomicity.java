package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author  wincher
 * @date 24/08/2017
 * static volatile变量只具有可见性，并不能保证原子性，因为如果两个线程同时读取共享内存的值然后更改操作，那么写回的时候就会覆盖掉一个值
 * AtomicX可以保证在多线程的原子性（注意atomic类只保证本身方法原子性，并不保证多次操作的原子性，
 * 也就是说如果多个,atomic操作在非同步代码块或方法中那么是不能够保证不被其他的线程中断的）
 */
public class AtomicClassAtomicity extends Thread {
    private static volatile int volatileCount;
//    线程同步的int封装类
    private static AtomicInteger atomicCount = new AtomicInteger(0);
    
    private static void addCount() {
        int totalTimes = 1000;
        for (int i = 0; i < totalTimes; i++) {
            volatileCount++;
            atomicCount.incrementAndGet(); //++
			//添加下面后证明了,atomicX并不保证多次操作的原子性
			try {
				TimeUnit.MICROSECONDS.sleep(2); // 添加延迟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			atomicCount.decrementAndGet();
        }

        System.out.println("count: " + volatileCount + "----atomicCount: " + atomicCount);
    }
    @Override
    public void run() {
        addCount();
    }

    public static void main(String[] args) {
        int totalTimes = 10;
        AtomicClassAtomicity[] arr = new AtomicClassAtomicity[totalTimes];
        for (int i = 0; i < totalTimes; i++) {
            arr[i] = new AtomicClassAtomicity();
            arr[i].start();
		}
        for (int i = 0; i < totalTimes; i++) {
			try {
				arr[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
    }
}
