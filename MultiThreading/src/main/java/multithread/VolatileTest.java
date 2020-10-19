package multithread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date 2019-09-06
 * <p> multithread <p>
 */
public class VolatileTest {
  
  //volatile只能保证可见性,不能保证原子性
  private static int i = 0;
  
  public int getI() {
    return i;
  }
  
  public void setI(int i) {
    VolatileTest.i = i;
  }
  
  public void run() {
    int c = new Random().nextInt(1000);
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    setI(c);
    System.out.println(c + ":" + getI());
  }
  
  public static void main(String[] args) {
    VolatileTest v = new VolatileTest();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
    new Thread( () -> v.run()).start();
  }
}
