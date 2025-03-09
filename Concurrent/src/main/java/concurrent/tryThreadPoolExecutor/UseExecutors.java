package concurrent.tryThreadPoolExecutor;

import java.util.concurrent.*;


/**
 * @author wincher
 * @date 02/09/2017
 */
public class UseExecutors {
	public static void main(String[] args) {
		ExecutorService pool = new ThreadPoolExecutor(5,
				10,
				100L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		//newFixedThreadFull
	}
}
