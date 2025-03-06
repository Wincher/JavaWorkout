package concurrent;


/**
  *@author wincher
  * @date 2019-09-06
  * <p> multithreading <p>
  */
public class VolitileAtomicity {

  private volatile static int i = 0;

  public static int getI() {
    return i;
  }

  public static synchronized void incrementI() {
    i++; // 非原子操作,添加synchronized即可保证原子性
  }

  public void run() {
    for (int j = 0; j < 1000; j++) { // 增加循环次数，增加并发冲突
      incrementI();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    VolitileAtomicity v = new VolitileAtomicity();
    Thread[] threads = new Thread[12];
    for (int j = 0; j < 12; j++) {
      threads[j] = new Thread(v::run);
      threads[j].start();
    }
    for(Thread thread : threads){
      thread.join();
    }
    System.out.println("Final i: " + getI());//result should be 12*1000
  }
}