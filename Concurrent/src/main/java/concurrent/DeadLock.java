package concurrent;

import lombok.Setter;

@Setter
public class DeadLock implements Runnable {

	private String tag;
	private static final Object lock1 = new Object();
	private static final Object lock2 = new Object();

	public static void main(String[] args) {
		DeadLock d1 = new DeadLock();
		d1.setTag("a");
		DeadLock d2 = new DeadLock();
		d2.setTag("b");
		Thread t1 = new Thread(d1, "t1");
		Thread t2 = new Thread(d2, "t2");
		t1.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		t2.start();
	}

	@Override
	public void run() {
		String A_SYMBOL = "a";
		String B_SYMBOL = "b";
		if (tag.equals(A_SYMBOL)) {
			synchronized (lock1) {
				try {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock1..");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				synchronized (lock2) {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock2..");
				}
			}
		}
		if (tag.equals(B_SYMBOL)) {
			synchronized (lock2) {
				try {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock2..");
					Thread.sleep(1002);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				synchronized (lock1) {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock1..");
				}
			}
		}
	}
}
