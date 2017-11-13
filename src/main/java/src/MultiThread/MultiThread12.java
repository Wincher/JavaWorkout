package MultiThread;

import java.util.*;

/**
 * Created by wincher on 26/08/2017.
 * 同步类容器
 * 同步类容器都是线程安全的，但在某些场景下可能需要加锁来保护复合操作，复合类操作如：迭代，跳转，条件运算，
 * 当这些操作并发修改容器时，可能会出现意外的行为，最经典的便是ConcurrentModificationException，
 * 原因是当容器迭代的过程中，被并发的修改了内容，这是由于早起迭代器设计的时候并没有考虑并发修改的问题。
 */
public class MultiThread12 {
	public static void main(String[] args) {
		//初始化火车票池并添加火车票：避免线程同步可采用Vector替代ArrayList HashTable替代HashMap
		final Vector<String> tickets = new Vector<String>();
		
		//Map<String,String> map = Collections.synchronizedMap(new HashMap<String,String>());
		for (int i = 1; i <= 1000; i++) {
			tickets.add("ticket:" + i);
		}
		
//		for (Iterator iterator = tickets.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			tickets.remove(string);
//		}
		
		for (int i = 1; i <= 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){
						if (tickets.isEmpty()) break;
						System.out.println(Thread.currentThread().getName() + "---" + tickets.remove(0));
					}
				}
			}).start();
		}
	}
}
