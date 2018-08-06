package multithread.multithread16;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author incher
 * @date 31/08/2017
 */
public class Main {
	public static void main(String[] args) {
		
		//内存缓冲区,线程安全
		BlockingQueue<Data> queue = new LinkedBlockingDeque<>(10);
		//生产者
		Provider p1 = new Provider(queue);
		Provider p2 = new Provider(queue);
		Provider p3 = new Provider(queue);
		
		//消费者
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		Consumer c3 = new Consumer(queue);
		
		//创建线程池运行，这是一个缓存的线程池，可以创建无穷大的线程，没有任务的时候不创建线程，空闲线程存活时间为60s（默认值）
///		ExecutorService cachePool = Executors.newCachedThreadPool();
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    ExecutorService cachePool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        60L, TimeUnit.SECONDS,
        new SynchronousQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		cachePool.execute(p1);
		cachePool.execute(p2);
		cachePool.execute(p3);
		cachePool.execute(c1);
		cachePool.execute(c2);
		cachePool.execute(c3);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		p1.stop();
		p2.stop();
		p3.stop();
		
		cachePool.shutdown();
	}
	
}
