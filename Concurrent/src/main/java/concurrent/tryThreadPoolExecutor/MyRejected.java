package concurrent.tryThreadPoolExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wincher
 * @date 02/09/2017
 */
public class MyRejected implements RejectedExecutionHandler {
	public MyRejected() {
	
	}
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("自定义处理...当前被拒绝的任务为: " + r.toString());
		// Task[]  依赖toString
	}
}
