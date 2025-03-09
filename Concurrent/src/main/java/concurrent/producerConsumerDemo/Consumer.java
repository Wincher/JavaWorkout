package concurrent.producerConsumerDemo;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author wincher
 * @date 31/08/2017
 */
public class Consumer implements Runnable {
	private final BlockingQueue<Data> queue;
	
	public Consumer(BlockingQueue<Data> queue) {
		this.queue = queue;
	}
	
	private final Random r = new Random();
	
	@Override
	public void run() {
		while (true) {
			try {
				//get Data
				Data data = this.queue.take();
				//进行数据处理，休眠100 - 1000毫秒模拟耗时
				Thread.sleep(r.nextInt(100,1000));
				System.out.println(Thread.currentThread().getName() + ", 消费成功，消费数据为id： " + data.getId());
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " " + e.getMessage());
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
}

