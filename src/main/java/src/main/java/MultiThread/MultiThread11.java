package MultiThread;

/**
 * Created by wincher on 25/08/2017.
 * 单例模式保证线程安全
 */
public class MultiThread11 {
	public static void main(String[] args) {
		//new MultiThread11().testDoubleSingleton();
		new MultiThread11().testSingleton();
	}
	
	private void testDoubleSingleton() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(DoubleSingleton.getDs().hashCode());
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(DoubleSingleton.getDs().hashCode());
			}
		},"t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(DoubleSingleton.getDs().hashCode());
			}
		},"t3");
		t1.start();
		t2.start();
		t3.start();
	}
	private void testSingleton() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singleton.getInstance().hashCode());
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singleton.getInstance().hashCode());
			}
		},"t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Singleton.getInstance().hashCode());
			}
		},"t3");
		t1.start();
		t2.start();
		t3.start();
	}
}

class DoubleSingleton {
	private static DoubleSingleton ds;
	
	public static DoubleSingleton getDs(){
		if (ds == null) {
			try {
				//模拟初始化对象的准备时间...
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (DoubleSingleton.class) {
				if (ds == null) {
					ds = new DoubleSingleton();
				}
			}
		}
		return ds;
	}
}

class Singleton {
	private static class InnerSingleton {
		private static Singleton single = new Singleton();
	}
	
	
	public static Singleton getInstance(){
		return InnerSingleton.single;
	}
}