package concurrent;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wincher
 * @date 26/08/2017
 * 同步类容器使用
 */
public class TrySynchronizedContainer {
	public static void main(String[] args) throws InterruptedException {
		useConcurrentMap();
		useCopyOnWrite();
		useQueue();
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

	private static void useCopyOnWrite() {
		CopyOnWriteArrayList<String> cwal = new CopyOnWriteArrayList<>();
		CopyOnWriteArraySet<String> cwas = new CopyOnWriteArraySet<>();

		cwal.add("hello");
		cwas.add("world");
		System.out.println(cwal + " " + cwas);
	}

	private static void useQueue() throws InterruptedException {
		tryConcurrentLinkedQueue();
		tryArrayBlockingQueue();
		tryLinkedBlockingQueue();
		trySynchronousQueue();
		tryPriorityBlockingQueue();


	}

	private static void tryPriorityBlockingQueue() {
		//带有优先级的阻塞队列
		PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<Task>();

		Task t1 = new Task(3, "san");
		Task t2 = new Task(4,"si");
		Task t3 = new Task(1, "yi");

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
	}

	private static void trySynchronousQueue() {
		//没有缓冲的队列，生产者产生的数据直接会被消费者获取并消费
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
	}

	private static void tryLinkedBlockingQueue() {
		LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		q.offer("e");
		q.add("f");
		System.out.println(q.size());
	}

	private static void tryArrayBlockingQueue() throws InterruptedException {
		ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(5);
		array.add("a");
		array.add("b");
		array.add("c");
		array.add("d");
		array.add("e");
		array.add("f");
		System.out.println(array.offer("a",3 ,TimeUnit.SECONDS));
	}

	private static void tryConcurrentLinkedQueue() {
		//高性能无阻塞无界队列：ConcurrentLinkedQueue
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
	}


	@Setter
	@Getter
	static class Task implements Comparable<Task> {

		private int id;
		private String name;

		public Task(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int compareTo(Task o) {
			return Integer.compare(this.id, o.id);
		}

		@Override
		public String toString() {
			return "Task{" + "id=" + id + ", name='" + name + '\'' + '}';
		}
	}


}