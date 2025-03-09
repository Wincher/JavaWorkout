package concurrent;

/**
 * @author wincher
 * @date 25/08/2017.
 * ThreadLocal
 */
public class TryThreadLocal {
  	public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public void getTh() {
		System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
	}
	
	public void setTh(String value) {
		threadLocal.set(value);
	}
	
	public static void main(String[] args) throws InterruptedException {
		final TryThreadLocal m = new TryThreadLocal();
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
		t1.join();
		t2.join();
	}
}
