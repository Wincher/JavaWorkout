package MultiThread;


/**
 * Created by wincher on 25/08/2017.
 * 锁
 */
public class MultiThread09 {
	public static void main(String[] args) {
		//new MultiThread09().testDeadLock();
		//new MultiThread09().testChangeLock();
		//new MultiThread09().testObjectLock();
		//new MultiThread09().testModifyLock();
		new MultiThread09().testStringLock();
	}
	
	private void testStringLock() {
		final StringLock m = new StringLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				m.method();
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				m.method();
			}
		},"t2");
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
	}
	
	private void testModifyLock() {
		final ModifyLock m = new ModifyLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				m.changeAttribute("zhangsan",20);
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				m.changeAttribute("wanger",25);
			}
		},"t2");
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
	}
	
	private void testObjectLock() {
		final ObjectLock objLock = new ObjectLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				objLock.method1();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				objLock.method2();
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				objLock.method3();
			}
		});
		t1.start();
		t2.start();
		t3.start();
	}
	
	private void testDeadLock() {
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
			e.printStackTrace();
		}
		t2.start();
	}
	
	private void testChangeLock() {
		ChangeLock c = new ChangeLock();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				c.method();
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				c.method();
			}
		}, "t2");
		t1.start();
		t2.start();
		
	}
}
class ChangeLock {
	private String lock = "lock";
	
	protected void method() {
		synchronized (lock) {
			try {
				System.out.println("current thread: " + Thread.currentThread().getName() + "Start..");
				//由于锁被更换，其他线程不会与此线程继续同步
				lock = "change lock";
				Thread.sleep(2000);
				System.out.println("current thread: " + Thread.currentThread().getName() + "Stop..");
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
class DeadLock implements Runnable {
	
	private String tag;
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	
	public void setTag(String tag){
		this.tag = tag;
	}
	@Override
	public void run() {
		if (tag.equals("a")) {
			synchronized (lock1) {
				try {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock1..");
					Thread.sleep(2001);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock2..");
				}
			}
		}
		if (tag.equals("b")) {
			synchronized (lock2) {
				try {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock1..");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock1) {
					System.out.println("current thread: " + Thread.currentThread().getName() + "enter lock2..");
				}
			}
		}
	}
}

class ObjectLock {
	public void method1(){
		synchronized (this) { //对象锁
			try {
				System.out.println("do method1");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void method2(){
		synchronized (ObjectLock.class) { //类锁
			try {
				System.out.println("do method2");
				Thread.sleep(2001);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private Object lock = new Object();
	public void method3(){
		synchronized (lock) { //任何对象锁
			try {
				System.out.println("do method3");
				Thread.sleep(2002);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

/**
 * 统一对象谁能够的修改不会影戏那个锁的情况
 */
class ModifyLock {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public synchronized void changeAttribute(String name, int age) {
		try {
			System.out.println("current thread: " + Thread.currentThread().getName() + " start..");
			this.setAge(age);
			this.setName(name);
			
			System.out.println("current thread: " + Thread.currentThread().getName() + " attribute changed to："
				+ this.getName() + ", " + this.getAge());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * synchronized代码块对字符串的锁，注意string常量池的缓存功能
 * new对象是新的是不同的锁，如果使用 "字符串常量" 那么将是统一把锁
 */
class StringLock {
	public void method() {
		//"字符串常量"
		synchronized (new String("字符串常量")) {
			try {
				while(true) {
					System.out.println("current thread: " + Thread.currentThread().getName() + "Start..");
					Thread.sleep(1000);
					System.out.println("current thread: " + Thread.currentThread().getName() + "Stop..");
				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}