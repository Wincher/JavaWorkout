package concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 通过网吧上机下机的例子使用,带有时间延迟的队列
 */
public class TryDelayQueue {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("NetBar start running");
		NetBar netBar = new NetBar();
		Thread onNet  = new Thread(netBar);
		onNet.start();

		netBar.checkIn("zhangsan","123",2);
		netBar.checkIn("lisi","124",5);
		netBar.checkIn("wangwu","125",3);
		netBar.checkIn("wangermazi","126",0);

		Thread.sleep(2000);
		netBar.setRunning(false);
		onNet.join();
	}
}

class NetBar implements Runnable {

	private DelayQueue<Person> queue = new DelayQueue<>();
	private volatile boolean isRunning = true;
	private final long startTime;

	NetBar() {
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		//循环,模拟每分钟检查一次下机用户
		while(isRunning) {
			Person p = queue.poll();
			if (p != null) {
				checkOut(p);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(1 * 10);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				throw new RuntimeException(e);
			}
		}
		close();
	}

	private void close() {
		if (!queue.isEmpty()) {
			System.out.println("check out customer...");
			for (Object p :queue.toArray()) {
				System.out.println(p.toString());
			}
		} else {
			System.out.println("no customer in...");
		}
		System.out.println("NetBar closed...");
	}

	public void checkIn(String name, String id, int money) {
		//方便模拟,将1000ms缩小100倍到10ms
		if (money <= 0) {
			System.out.println("not a valid money");
			return;
		}
		Person p = new Person(name, id, 60 * 10 * money + System.currentTimeMillis());
		System.out.println("name: " + p.getName() + ", IdCard: " + p.getId() + ", money: " + money +" yuan, checked in...");
		this.queue.add(p);
	}

	public void checkOut(Person person) {
		System.out.println("name: " + person.getName() + ", IdCard: " + person.getId() + " time end");
	}

	public boolean setRunning(boolean isRunning) {
		return this.isRunning = isRunning;
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

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", endTime=" + endTime +
				", timeUnit=" + timeUnit +
				'}';
	}

	/**
	 * 用来判断是否到了截止时间
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
