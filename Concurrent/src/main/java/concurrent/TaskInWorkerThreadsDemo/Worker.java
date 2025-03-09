package concurrent.TaskInWorkerThreadsDemo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *  @author wincher
 *  @date 31/08/2017
 */
public class Worker implements Runnable {
	private ConcurrentLinkedQueue<Task> workQueue;
	private ConcurrentHashMap<String, Object> resultMap;
	
	public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
		this.workQueue = workQueue;
	}
	
	public void setResultMap(ConcurrentHashMap<String,Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	@Override
	public void run() {
		while (true) {
			Task task = this.workQueue.poll();
			if (task == null) {
				break;
			}
			Object result = handle(task);
			this.resultMap.put(Integer.toString(task.getId()), result);
		}
	}
	
	private Object handle(Task task) {
		Object result = null;
		try {
			Thread.sleep(500);
			result = task.getPrice();
			System.out.println(Thread.currentThread().getName() + " consume task " + task.getId());
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
}
