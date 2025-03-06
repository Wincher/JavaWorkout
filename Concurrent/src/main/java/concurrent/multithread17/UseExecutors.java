package concurrent.multithread17;

import java.util.concurrent.*;


/**
 * @author wincher
 * @date 02/09/2017
 */
public class UseExecutors {
	public static void main(String[] args) {
		ExecutorService pool = new ThreadPoolExecutor(10, 10,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		
		//newFixedThreadFull
	}
}
