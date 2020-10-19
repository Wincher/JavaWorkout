package NetworkPrograming.BIO2;

import NetworkPrograming.BIO.ServerHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date   14/09/2017.
 */
public class HandlerExecutorPool {
	
	private ExecutorService executor;
	
	public HandlerExecutorPool(int maxPoolSie, int queueSize) {
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				maxPoolSie,
				120L,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize)
		);
	}
	
	public void execute(ServerHandler serverHandler) {
		this.executor.execute(serverHandler);
	}
}
