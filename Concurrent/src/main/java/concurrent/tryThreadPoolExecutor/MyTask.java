package concurrent.tryThreadPoolExecutor;


/**
 * @author wincher
 * @date 02/09/2017
 */
public class MyTask implements Runnable {
	
	private int taskId;
	private String taskName;
	
	public MyTask(int taskId, String taskName) {
		this.taskId = taskId;
		this.taskName = taskName;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("run taskId = " + taskId);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			System.err.println(Thread.currentThread().getName() + " " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "Task ID: " + taskId;
	}
}