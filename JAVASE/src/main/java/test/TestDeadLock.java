package test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestDeadLock extends Thread {
    private String first;
    private String second;

    public TestDeadLock(String name, String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained: " + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained: " + second);
                }
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = () -> {
            long[] threadIds = mbean.findDeadlockedThreads();
            if (threadIds != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(threadIds);
                System.out.println("Detected deadlock threads:");
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println(threadInfo.getThreadName());
                }
            }
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // 稍等 5 秒，然后每 10 秒进行一次死锁扫描
        scheduler.scheduleAtFixedRate(dlCheck, 5L, 10L, TimeUnit.SECONDS);
        // 死锁样例代码…

    String lockA = "lockA";
    String lockB = "lockB";
    TestDeadLock t1 = new TestDeadLock("Thread1", lockA, lockB);
    TestDeadLock t2 = new TestDeadLock("Thread2", lockB, lockA);
        t1.start();
        t2.start();
        t1.join(10000);
        t2.join(5000);
}
}

