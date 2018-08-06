package multithread;

/**
 * @author wincher
 * @date 25/08/2017.
 * ThreadLocal
 */
public class MultiThread10 {
  public static ThreadLocal<String> th = new ThreadLocal<>();
	
	public void getTh() {
		System.out.println(Thread.currentThread().getName() + ":" + th.get());
	}
	
	public void setTh(String value) {
		th.set(value);
	}
	
	public static void main(String[] args) {
		final MultiThread10 m = new MultiThread10();
		Thread t1 = new Thread(() -> {
      m.setTh("Messi");
      m.getTh();
    },"t1");
		Thread t2 = new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      m.getTh();
    },"t2");
		t1.start();
		t2.start();
	}
}
