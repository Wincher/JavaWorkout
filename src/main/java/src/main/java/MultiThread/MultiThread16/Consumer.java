package MultiThread.MultiThread16;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by wincher on 31/08/2017.
 */
public class Consumer implements Runnable {
	private final BlockingQueue<Data> queue;
	
	public Consumer(BlockingQueue<Data> queue) {
		this.queue = queue;
	}
	
	private Random r = new Random();
	
	@Override
	public void run() {
		while (true) {
			try {
				//get Data
				Data data = this.queue.take();
				//进行数据处理，休眠0 - 1000毫秒模拟耗时
				Thread.sleep(r.nextInt(1000));
				System.out.println("Current Thread:" + Thread.currentThread().getName() +
					", 消费成功，消费数据为id： " + data.getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

