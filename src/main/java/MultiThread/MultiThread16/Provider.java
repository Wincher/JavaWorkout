package MultiThread.MultiThread16;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wincher on 31/08/2017.
 */
public class Provider implements Runnable {
	//共享缓存区
	private BlockingQueue<Data> queue;
	//多线程之间是否启动变量，有强制从内存中刷新的功能，即时返回线程的状态
	private volatile boolean isRunning = true;
	//id生成器
	private static AtomicInteger count = new AtomicInteger();
	//随机对象
	private static Random r = new Random();
	
	public Provider(BlockingQueue<Data> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (isRunning) {
			try {
				//随机休眠0-1000毫秒，表示获取数据（产生数据的耗时）
				Thread.sleep(r.nextInt(1000));
				//获取的数据进行累计...
				int id = count.incrementAndGet();
				//比如通过一个getData()方法获取了
				Data data = new Data(Integer.toString(id),"数据" + id);
				System.out.println("Current Thread:" + Thread.currentThread().getName() +
						", received data id is:" + id + ", load to cached thread pool...");
				if (!this.queue.offer(data, 2, TimeUnit.SECONDS)) {
					System.out.println("load to cached thread pool failed!!!");
					//do something... like reload...
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		this.isRunning = false;
	}
}
