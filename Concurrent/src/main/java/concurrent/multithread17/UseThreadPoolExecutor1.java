package concurrent.multithread17;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date 02/09/2017
 * 在使用有界队列时，如果新任务要执行时，如果线程池实际数量小于corePoolSize，则优先创建线程，
 * 若大于corePoolSize，则会将任务加入队列，若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
 * 若线程数大于maximumPoolSize,则执行拒绝策略。或者其他自定义方式。
 */
public class UseThreadPoolExecutor1 {
	public static void main(String[] args) {
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(
				1,
				2,
				60,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(3)
				//,new ThreadPoolExecutor.DiscardOldestPolicy()
				//new ThreadPoolExecutor.AbortPolicy()
				//new ThreadPoolExecutor.DiscardPolicy()
				//new ThreadPoolExecutor.CallerRunsPolicy()
				,new MyRejected()
		);
		
		MyTask mt1 = new MyTask(1,"task1");
		MyTask mt2 = new MyTask(2,"task2");
		MyTask mt3 = new MyTask(3,"task3");
		MyTask mt4 = new MyTask(4,"task4");
		MyTask mt5 = new MyTask(5,"task5");
		MyTask mt6 = new MyTask(6,"task6");
		
		pool.execute(mt1);
		pool.execute(mt2);
		pool.execute(mt3);
		pool.execute(mt4);
		pool.execute(mt5);
		pool.execute(mt6);
		
		pool.shutdown();
		
	}
}
