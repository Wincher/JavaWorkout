package concurrent;


import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Lock;

/**
 * @author wincher
 * @date 25/08/2017
 * Lock
 */
public class LockTypes {
    
    static LockTypes lt = new LockTypes();
    
    public static void main(String[] args) throws InterruptedException {
//        lt.testStringLock();
//        lt.testModifyLock();
//        lt.testObjectLock();
        lt.testChangeLock();
    }
    
    /**
     * 证明new String加的不是同一个锁,可以并行执行的
	 synchronized代码块对字符串的锁，注意string常量池的缓存功能
	 new对象是新的是不同的锁，如果使用 "字符串常量" 那么将是统一把锁
	 */
	public void testStringLock() throws InterruptedException {
		Runnable r = () -> {
			//替换为new String("字符串常量")后t2线程和t1线程带有不同的锁,各自执行不影响
			synchronized ("字符串常量") {
				try {
					System.out.println("Thread: " + Thread.currentThread().getName() + " Start..");
					Thread.sleep(3000);
					System.out.println("Thread: " + Thread.currentThread().getName() + " Stopped..");
				} catch (InterruptedException e){
					throw new RuntimeException(e);
				}
			}
		};
		Thread t1 = new Thread(r,"t1");
		Thread t2 = new Thread(r,"t2");
		t1.start();
		Thread.sleep(1000);
		t2.start();
	}
	
	public void testModifyLock() throws InterruptedException {
		final ModifyLock m = new ModifyLock();
		Thread t1 = new Thread(() -> m.changeAttribute("Messi",20),"t1");
		Thread t2 = new Thread(() -> m.changeAttribute("wincher",25),"t2");
		t1.start();
		Thread.sleep(100);
		t2.start();
	}
    
    public void testObjectLock() {
//		证明三个方法的三个锁是互相不影响的,将r1,r2,r3换成同一个锁,线程互相阻塞
		Runnable r1 = () -> {
			//对象锁
			synchronized (this) {
				try {
					System.out.println(Thread.currentThread().getName() + ": method1...");
					Thread.sleep(2000);
					System.out.println(11);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		};
		Runnable r2 = () -> {
			//类锁
			synchronized (LockTypes.class) {
				try {
					System.out.println(Thread.currentThread().getName() + ": method2...");
					Thread.sleep(3000);
					System.out.println(12);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		};
		final Object lock = new Object();
		Runnable r3 = () -> {
			//任何对象锁
			synchronized (lock) {
				try {
					System.out.println(Thread.currentThread().getName() + ": method3...");
					Thread.sleep(4000);
					System.out.println(13);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		};
		new Thread(r1).start();
		new Thread(r2).start();
		new Thread(r3).start();
	}
	
    public void testChangeLock() {
		//必须要final修饰,数组内的元素可以修改, 直接String无法修改
		//String[] lock 是为了满足 Lambda 表达式的变量捕获规则，确保线程安全。
		//使用数组是为了绕过lambda表达式对于final的限制。
		final String[] lock = {"lock"};
		Runnable r = () -> {
			synchronized (lock[0]) {
				try {
					System.out.println("current thread: " + Thread.currentThread().getName() + "Start..");
					//由于锁被更换，其他线程不会与此线程继续同步
					lock[0] = "change lock";
					Thread.sleep(2000);
					System.out.println("current thread: " + Thread.currentThread().getName() + "Stop..");
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		};
		Thread t1 = new Thread(r, "t1");
		Thread t2 = new Thread(r, "t2");
		t1.start();
		//sleep 50ms 是这位了段代码体现效果,没有t1,t2都获取的是"lock",为锁
		//加上后,由于lock已经变为"changed lock", 这两个线程加的不同的锁,为异步操作
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
		
	}
}

/**
 * 同一对象对内属性的修改不会影响锁的效果
 */
@Setter
@Getter
class ModifyLock {
	private String name = "a";
	private int age;

	public void changeAttribute(String name, int age) {
		System.out.println("current thread: " + Thread.currentThread().getName() + " start..");
		synchronized (this) {
			this.setAge(age);
			this.setName(name);
			System.out.println("current thread: " + Thread.currentThread().getName() + " attribute modified to："
					+ this.getName() + ", " + this.getAge());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}