package concurrent;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * @date 25/08/2017
 * 模拟阻塞队列BlockingQueue
 * @author wincher
 */
public class MyBlockingQueue {

    //需要一个承装元素的集合
    private final LinkedList<Object> list = new LinkedList<>();
    //需要一个计数器
    private final AtomicInteger count = new AtomicInteger(0);
    //需要制定上限和下限
    private final int minSize = 0;
    private final int maxSize;

    public MyBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }
    
    /**
     * 初始化一个对象，用于加锁
     */
    private final Object lock = new Object();
    
    /**
     * put(object):把object加到blockingQueue里，如果BlockQueue没有空间，
     * 则调用此方法的线程被阻断，指导BlockingQueue里面有空间再继续
	 */
    public void put(Object obj){
        synchronized (lock){
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
            //加入元素
            list.add(obj);
            //计数器累加
            count.incrementAndGet();
            //唤醒其他线程
            lock.notify();
            System.out.println(Thread.currentThread().getName() + ": new element added: " + obj);
        }
    }
    
    /**
     * take：取走BlockingQueue里排在首位的对象，若BlockingQueue为空，
     * 阻断进入等待状态指导BlockingQueue有新的数据被加入
	 */
    public Object take() {
        Object ret;
        synchronized (lock) {
            while (count.get() == this.minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e){
                    System.err.println(e.getMessage());
                }
            }
            //做移除元素操作
            ret = list.removeFirst();
            //计数器递减
            count.decrementAndGet();
            //通知另外一个线程
            lock.notify();
        }
        return ret;
    }

    public int getSize(){
        return this.count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue m = new MyBlockingQueue(5);
        m.put("a");
        m.put("b");
        m.put("c");
        m.put("d");
        m.put("e");
        System.out.println(Thread.currentThread().getName() + ": list length is: " + m.getSize());

        Thread t1 = new Thread(() -> {
            m.put("f");
            m.put("g");
            m.put("h");
        },"t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            m.put("i");
            m.put("j");
            m.put("k");
        },"t2");
        t2.start();
        for (int i = 0; i < 6; i++) {
            System.out.println(Thread.currentThread().getName() + ": removed element is: " + m.take() + ", current list:" + m.list);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
