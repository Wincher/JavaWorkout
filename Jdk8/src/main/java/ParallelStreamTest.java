import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author huwq
 * @since 2018/8/12
 * <p> PACKAGE_NAME <p>
 */
public class ParallelStreamTest {

  private static List<Integer> list1 = new ArrayList<>();
  private static List<Integer> list2 = new ArrayList<>();
  private static List<Integer> list3 = new ArrayList<>();
  private static Lock lock = new ReentrantLock();
  
  public static void main(String[] args) {
    //testParallel();
    testB();
  }
  
  public static void testB() {
    // 构造一个10000个元素的集合
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      list.add(i);
    }
    System.out.println("list size: " + list.size());
    // 统计并行执行list的线程
    Set<Thread> threadSet = new CopyOnWriteArraySet<>();
    // 并行执行
    list.parallelStream().forEach(integer -> {
      Thread thread = Thread.currentThread();
      ///System.out.println(thread);
      // 统计并行执行list的线程
      threadSet.add(thread);
    });
    System.out.println("threadSet一共有" + threadSet.size() + "个线程");
    System.out.println("系统一个有"+Runtime.getRuntime().availableProcessors()+"个cpu");
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
      list1.add(i);
      list2.add(i);
    }
    Set<Thread> threadSetTwo = new CopyOnWriteArraySet<>();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    Thread threadA = new Thread(() -> {
      list1.parallelStream().forEach(integer -> {
        Thread thread = Thread.currentThread();
        /// System.out.println("list1" + thread);
        threadSetTwo.add(thread);
      });
      countDownLatch.countDown();
    });
    Thread threadB = new Thread(() -> {
      list2.parallelStream().forEach(integer -> {
        Thread thread = Thread.currentThread();
        /// System.out.println("list2" + thread);
        threadSetTwo.add(thread);
      });
      countDownLatch.countDown();
    });
  
    threadA.start();
    threadB.start();
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.print("threadSetTwo一共有" + threadSetTwo.size() + "个线程");
  
    System.out.println("---------------------------");
    System.out.println(threadSet);
    System.out.println(threadSetTwo);
    System.out.println("---------------------------");
    threadSetTwo.addAll(threadSet);
    System.out.println(threadSetTwo);
    System.out.println("threadSetTwo一共有" + threadSetTwo.size() + "个线程");
    System.out.println("系统一个有"+Runtime.getRuntime().availableProcessors()+"个cpu");
  }
  
  
  public static void testParallel() {
    
    IntStream.rangeClosed(1, 10000).forEach(list1::add);
    System.out.println(IntStream.rangeClosed(1, 1000).sum());
  
    //会出现异常很奇怪
    IntStream.rangeClosed(1, 10000).parallel().forEach(list2::add);
    System.out.println(IntStream.rangeClosed(1, 1000).parallel().sum());
  
    IntStream.rangeClosed(1, 10000).forEach(i -> {
      lock.lock();
      try {
        list3.add(i);
      } finally {
        lock.unlock();
      }
    });
  
    System.out.println("串行执行的大小：" + list1.size());
    System.out.println("并行执行的大小：" + list2.size());
    System.out.println("加锁并行执行的大小：" + list3.size());
  }
}


