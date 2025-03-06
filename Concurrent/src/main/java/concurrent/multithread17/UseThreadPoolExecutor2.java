package concurrent.multithread17;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wincher
 * @date 02/09/2017
 * 在使用无界队列时:LinkedBlockingQueu.
 * 与有界队列相比，除非系统资源耗尽，否则无界的任务队列不存在任务入队失败的情况，
 * 当有新任务到来，系统的线程数小于corePoolSize时，则新建线程执行任务，
 * 当达到corePoolSize后，就不会继续增加，若后续仍有新的任务加入，而没有空闲的线程资源，
 * 则任务直接进入队列等待，
 * 若任务创建和处理的速度差异很大，无界队列会保持快速增长，指导耗尽系统内存。
 *
 */
public class UseThreadPoolExecutor2 implements Runnable{
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	public static void main(String[] args) {
		
		System.out.println("核心数:" + Runtime.getRuntime().availableProcessors());
		BlockingQueue<Runnable> queue =
				new LinkedBlockingQueue<>();
				//new ArrayBlockingQueue<Runnable>(10);
		ExecutorService executor = new ThreadPoolExecutor(
				5,
				10, //无界队列这个参数和corePoolSize一般相同
				120L,
				TimeUnit.SECONDS,
				queue
		);
		
		for (int i = 0; i < 20; i++) {
			executor.execute(new UseThreadPoolExecutor2());
		}
		
		try {
			Thread.sleep(1000);
			System.out.println("queue size :" + queue.size());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//优雅关闭,不会立刻关闭而是等任务执行完成再关闭
		executor.shutdown();
	}
	
	@Override
	public void run() {
		try {
			int temp = count.incrementAndGet();
			System.out.println("task" + temp);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
