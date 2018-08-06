package multithread;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wincher
 * @date 26/08/2017
 * 同步类容器使用
 */
public class MultiThread13 {
	public static void main(String[] args) {
		useConcurrentMap();
		useCopyOnWrite();
		useQueue();
		//带有时间延迟的队列
		useDelayQueue();
	}
	
	private static void useDelayQueue() {
		try {
			System.out.println("NetBar start running");
			NetBar netBar = new NetBar();
			Thread onNet  = new Thread(netBar);
			onNet.start();
			
			netBar.checkIn("zhangsan","123",1);
			netBar.checkIn("lisi","124",10);
			netBar.checkIn("wangwu","125",5);
			
			Thread.sleep(1000);
			
			netBar.isRunning = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void useQueue() {
		//高性能无阻塞无界队列：ConcurrentLinkedQueue
		/*
		ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		q.offer("e");
		q.add("f");
		System.out.println(q.size());
		System.out.println(q.poll());   //从头部取出队列，并从队列里删除元素
		System.out.println(q.size());
		System.out.println(q.peek());
		System.out.println(q.size());
		System.out.println(q);
		
		ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(5);
		array.add("a");
		array.add("b");
		array.add("c");
		array.add("d");
		array.add("e");
		array.add("f");
		System.out.println(array.offer("a",3 ,TimeUnit.SECONDS));
		*/
		
		//阻塞队列
		/*
		LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		q.offer("e");
		q.add("f");
		System.out.println(q.size());
		*/
		
		//没有缓冲的队列，生产者产生的数据直接会被消费者获取并消费
		/*
		final SynchronousQueue<String> q = new SynchronousQueue<String>();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(q.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t1.start();
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				q.add("hello world!");
			}
		});
		t2.start();
		*/
		
		
		
		//带有优先级的阻塞队列
		/*
		PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<Task>();
		
		Task t1 = new Task();
		t1.setId(3);
		t1.setName("id is 3");
		Task t2 = new Task();
		t2.setId(4);
		t2.setName("id is 4");
		Task t3 = new Task();
		t3.setId(1);
		t3.setName("id is 1");
		
		q.add(t1);
		q.add(t2);
		q.add(t3);
		System.out.println("queue:" + q);
		try {
			System.out.println(q.take().getId());
			
			System.out.println("queue:" + q);
			System.out.println(q.take().getId());
			System.out.println(q.take().getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		*/
		
		
		
	}
	private static void useCopyOnWrite() {
		CopyOnWriteArrayList<String> cwal = new CopyOnWriteArrayList<>();
		CopyOnWriteArraySet<String> cwas = new CopyOnWriteArraySet<>();
		
		cwal.add("hello");
		System.out.println(cwal);
	}
	
	private static void useConcurrentMap() {
		ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String,Object>();
		chm.put("k1","v1");
		chm.put("k2","v2");
		chm.put("k3","v3");
		chm.put("k4","v4");
		chm.put("k4","v5");
		//复合操作如果value存在不会存入，put则会覆盖原有value
		chm.putIfAbsent("k4","v6");
		
		for (Map.Entry<String, Object> me : chm.entrySet()) {
			System.out.println("key: " + me.getKey() + ", value: " + me.getValue());
		}
	}
}

class Task implements Comparable<Task> {
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(Task o) {
		return this.id > o.id ? 1 : (this.id < o.id ? -1 : 0);
	}
	
	@Override
	public String toString() {
		return "Task{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}

class NetBar implements Runnable {
	
	private DelayQueue<Person> queue = new DelayQueue<>();
	public boolean isRunning = true;
	
	public void checkIn(String name, String id, int money) {
		Person p = new Person(name, id, 1000 * money + System.currentTimeMillis());
		System.out.println("name: " + p.getName() + ", IdCard: " + p.getId() + ", money: " + money +"yuan, start...");
		this.queue.add(p);
	}
	
	public void checkOut(Person person) {
		System.out.println("name: " + person.getName() + ", IdCard: " + person.getId() + "time end");
	}
	
	@Override
	public void run() {
		while(isRunning) {
			try {
				Person p = queue.take();
				checkOut(p);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("stop NetBar...");
	}
}

class Person implements Delayed {
	
	private String name;
  /**
   * idCard
   */
	private String id;
  /**
   * 截止时间
   */
	private long endTime;
  /**
   * 定义时间工具类
   */
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	
	public Person(String name, String id, long endTime) {
		this.name = name;
		this.id = id;
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
  
  
  /**
   * 用来判断是否到了截止时间
   * @param unit
   * @return
   */
	@Override
	public long getDelay(TimeUnit unit) {
		return endTime - System.currentTimeMillis();
	}
	//
	@Override
	public int compareTo(Delayed o) {
		Person p = (Person)o;
		return this.getDelay(this.timeUnit) - p.getDelay(this.timeUnit) > 0 ? 1 : 0;
	}
}