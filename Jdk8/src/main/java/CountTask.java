import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author huwq
 * @since 2018/8/13
 * <p> PACKAGE_NAME <p>
 */
public class CountTask extends RecursiveTask<Integer> {
  private static final int THRESHOLD = 100;
  private final int start;
  private final int end;
  
  public CountTask(int start, int end) {
    this.start = start;
    this.end = end;
  }
  
  int sum = 0;
  
  @Override
  protected Integer compute() {
    
    // if task size is small enough
    boolean canCompute = (end - start) <= THRESHOLD;
    if (canCompute) {
      for (int i = start; i <= end; i++) {
        sum += i;
      }
      
      return sum;
    }
    // split
    int middle = (end + start) / 2;
    System.out.println("Middle:  " + middle);
    
    CountTask leftTask = new CountTask(start, middle);
    leftTask.fork();
    
    CountTask rightTask = new CountTask(1 + middle, end);
    
    int rightValue = rightTask.compute();
    int leftValue = leftTask.join();
    sum = leftValue + rightValue;
    
    return sum;
    
  }
  
  public static void main(String[] args) {
//    ForkJoinPool forkJoinPool = new ForkJoinPool();
//    CountTask task = new CountTask(1, 100);
//    Future result = forkJoinPool.submit(task);
//    try {
//      long startTime = System.nanoTime();
//      System.out.println(result.get() + " : " + (System.nanoTime() - startTime));
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } catch (ExecutionException e) {
//      e.printStackTrace();
//    }
    long start = System.nanoTime();
    long sum = 0;
//    for (long i = 0; i < 100_000_000; i++) {
//      sum += i;
//    }
//    System.out.println(sum + "time:" + ((System.nanoTime() - start) / 1_000_000));
//    long fastest = Long.MAX_VALUE;
//    long end;
//    System.out.println("********");
//    for (int i = 1; i <= 10; i++) {
//      start = System.nanoTime();
//      for (long ii = 0; ii < 100_000_000; ii++) {
//        sum += ii;
//      }
//      end = (System.nanoTime() - start) / 1_000_000;
//      fastest = (end < fastest) ? end : fastest;
//    }
//    System.out.println(sum + "time:" + fastest);
    
    
    
    long fastest = Long.MAX_VALUE;
    long end;
    System.out.println("********");
    for (int i = 1; i <= 10; i++) {
      start = System.nanoTime();
      IntStream.rangeClosed(1, 1_000_000_000).parallel().sum();
      end = (System.nanoTime() - start) / 1_000_000;
      fastest = (end < fastest) ? end : fastest;
    }
    System.out.println(sum + "time:" + fastest);
  }
}
