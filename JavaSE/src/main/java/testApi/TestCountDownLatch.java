package testApi;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

 
 

/**
 * @date 2019-09-20
 * <p> testApi <p>
 */
public class TestCountDownLatch {
 
  public static void main(String[] args) throws InterruptedException {
  
    for (int i = 0; i < 1 ; i++) {
      doSth();
      TimeUnit.SECONDS.sleep(10);
    }
  }
   
  private static void doSth() throws InterruptedException {
    ArrayList ssnArray = new ArrayList();
    ssnArray.add( "8602276472");
    ssnArray.add( "7005192112");
    ssnArray.add( "7709107473");
    ssnArray.add( "2604216990");
    ssnArray.add( "8106277331");
    final CountDownLatch endGate = new CountDownLatch(ssnArray.size()-1);
    final CountDownLatch startGate = new CountDownLatch(1);
    for(int i = 0; i < ssnArray.size(); i++) {
      final String ssn = ssnArray.get(i).toString();
      new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + "start");
          startGate.await();
          TimeUnit.SECONDS.sleep(new Random().nextInt(5));
          System.out.println(Thread.currentThread().getName() + "over");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          endGate.countDown();
        }
      }, "Thead: " + i).start();
    }
    long startTime = System.nanoTime();
    TimeUnit.SECONDS.sleep(5);
    System.out.println(startTime + " [" + Thread.currentThread() + "]ready, concurrent going...");
    startGate.countDown();
    endGate.await();
    long endTime = System.nanoTime();
    System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
  }

}