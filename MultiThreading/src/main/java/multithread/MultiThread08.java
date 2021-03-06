package multithread;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * @date 25/08/2017
 * 模拟阻塞队列BlockingQueue
 * @author wincher
 */
public class MultiThread08 {
    /**
     * 需要一个承装元素的集合
     */
    private LinkedList<Object> list = new LinkedList<>();
    /**
     * 需要一个计数器
     */
    private AtomicInteger count = new AtomicInteger(0);
    /**
     * 需要制定上限和下限
     */
    private final int minSize = 0;
    private final int maxSize;
    
    /**
     * constructor
     * @param maxSize
     */
    public MultiThread08(int maxSize) {
        this.maxSize = maxSize;
    }
    
    /**
     * 初始化一个对象，用于加锁
     */
    private final Object lock = new Object();
    
    /**
     * put(object):把object加到blockingQueue里，如果BlockQueue没有空间，
     * 则调用此方法的线程被阻断，指导BlockingQueue里面有空间再继续
     * @param obj
     */
    public void put(Object obj){
        synchronized (lock){
            while (count.get() == this.maxSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //加入元素
            list.add(obj);
            //计数器累加
            count.incrementAndGet();
            //唤醒其他线程
            lock.notify();
            System.out.println("new element added: " + obj);
        }
    }
    
    /**
     * take：取走BlockingQueue里排在首位的对象，若BlockingQueue为空，
     * 阻断进入等待状态指导BlockingQueue有新的数据被加入
     * @return
     */
    public Object take() {
        Object ret = null;
        synchronized (lock) {
            while (count.get() == this.minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
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

    public static void main(String[] args) {
        MultiThread08 m = new MultiThread08(5);
        m.put("a");
        m.put("b");
        m.put("c");
        m.put("d");
        m.put("e");
        System.out.println("list length is: " + m.getSize());

        Thread t1 = new Thread(() -> {
            m.put("f");
            m.put("g");
            m.put("h");
        },"t1");
        t1.start();
        Thread t3 = new Thread(() -> {
            m.put("i");
            m.put("j");
            m.put("k");
        },"t1");

        t3.start();

        Thread t2 = new Thread(() -> {
            System.out.println("removed element is: " + m.take());
            System.out.println("removed element is: " + m.take());
            System.out.println("removed element is: " + m.take());
            System.out.println("removed element is: " + m.take());
            System.out.println("removed element is: " + m.take());
            System.out.println("removed element is: " + m.take());
        },"t2");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
    }
}
