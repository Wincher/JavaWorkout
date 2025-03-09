package concurrent;

import java.util.*;

/**
 * @author wincher
 * @date 26/08/2017
 * 同步类容器
 * 同步类容器都是线程安全的，但在某些场景下可能需要加锁来保护复合操作，复合类操作如：迭代，跳转，条件运算，
 * 当这些操作并发修改容器时，可能会出现意外的行为，最经典的便是ConcurrentModificationException，
 * 原因是当容器迭代的过程中，被并发的修改了内容，这是由于早起迭代器设计的时候并没有考虑并发修改的问题。
 */
public class TryVector {
	public static void main(String[] args) throws InterruptedException {
		//初始化火车票池并添加火车票：避免线程同步可采用Vector替代ArrayList HashTable替代HashMap
		final Vector<String> tickets = new Vector<>();
		///Map<String,String> map = Collections.synchronizedMap(new HashMap<String,String>());
    	int a = 1000;
		for (int i = 1; i <= a; i++) {
			tickets.add("ticket:" + i);
		}
		System.out.println("total Stock:" + tickets.size());

///		for (Iterator iterator = tickets.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			tickets.remove(string);
//		}

		int thread_num = 10;
		int[] c = new int[tickets.size()];
		Thread[] threads = new Thread[thread_num];
		for (int i = 0; i < thread_num; i++) {
			Runnable run = () -> {
				while (!tickets.isEmpty()) {
					String ticketString = tickets.remove(0);
					int idx = Integer.valueOf(ticketString.split(":")[1]);
					System.out.println(Thread.currentThread().getName() + "---" + ticketString +
							" consumed, current stock: " + tickets.size());
					c[idx - 1]++;
				}
			};
			threads[i] = new Thread(run, "thread-" + i);
			threads[i].start();
		}
		for (int i = 0; i < thread_num; i++) threads[i].join();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 100; j++) {
				System.out.print(c[i*100+j]);
			}
			System.out.println();
		}

		/*
			results like:
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
			1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 */
	}
}
