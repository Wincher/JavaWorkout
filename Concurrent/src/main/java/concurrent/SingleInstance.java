package concurrent;

import org.checkerframework.dataflow.qual.Pure;

/**
 * @author wincher
 * @date 25/08/2017
 * 单例模式保证线程安全
 */
public class SingleInstance {

	public static void main(String[] args) {
		new SingleInstance().testSingleton(() -> System.out.println(DoubleCheckSingleton.getInstance().hashCode()));
		System.out.println("------------------");
		new SingleInstance().testSingleton(() -> System.out.println(DiyInnerSingleton.getInstance().hashCode()));
	}
	
	private void testSingleton(Runnable runnable) {

		for (int i = 0; i < 3; i++) {
			new Thread(runnable,"t1").start();
		}
	}
}

class DoubleCheckSingleton {

	private volatile static DoubleCheckSingleton instance;
	private DoubleCheckSingleton() {}
	
	public static DoubleCheckSingleton getInstance(){
		if (instance == null) {
			synchronized (DoubleCheckSingleton.class) {
				try {
					//模拟初始化对象的准备时间...
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				if (instance == null) {
					instance = new DoubleCheckSingleton();
				}
			}
		}
		return instance;
	}

}

class DiyInnerSingleton {

	private DiyInnerSingleton(){}

	private static class InnerSingleton {
		private static final DiyInnerSingleton single = new DiyInnerSingleton();
	}
	
	public static DiyInnerSingleton getInstance(){
		return InnerSingleton.single;
	}
}