package MultiThread.MultiThread15;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by wincher on 31/08/2017.
 */
public class Master {
	// 有一个盛放任务的容器
	private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<Task>();
	//需要有一个盛放worker的集合
	private HashMap<String, Thread> workers = new HashMap<String, Thread>();
	//需要有一个盛放每一个worker执行任务的结果集合
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
	
	//构造方法
	public Master(Worker worker,int workerCount) {
		worker.setWorkQueue(this.workQueue);
		worker.setResultMap(this.resultMap);
		
		for (int i = 0; i < workerCount; i++) {
			this.workers.put(Integer.toString(i), new Thread(worker));
		}
	}
	
	//需要一个提交任务的方法
	public void submit(Task task) {
		this.workQueue.add(task);
	}
	
	//需要一个执行的方法，启动所有worker方法并执行任务
	public void execute() {
		for (Map.Entry<String, Thread> me : workers.entrySet()) {
			me.getValue().start();
		}
	}
	
	// 判断是否运行结束的方法
	public boolean isComplete() {
		for (Map.Entry<String, Thread> me : workers.entrySet()) {
			if (me.getValue().getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		
		return true;
	}
	
	//计算结果方法
	public int getResult() {
		int priceResult = 0;
		for (Map.Entry<String,Object> me : resultMap.entrySet()) {
			priceResult += (Integer) me.getValue();
		}
		return priceResult;
	}
}