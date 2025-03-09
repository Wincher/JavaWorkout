package concurrent.TaskInWorkerThreadsDemo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * @date 31/08/2017
 * Master将task分给多个worker执行大幅减少时间,并收集结果
 */
public class Main {
	
	public static void main(String[] args) {
		Master master = new Master(new Worker() , 10);
		Random r = new Random();
		int expectedTotalPrice = 0;
		for (int i = 1; i <= 100; i++) {
			Task t = new Task();
			t.setId(i);
			int price = r.nextInt(1000);
			expectedTotalPrice += price;
			t.setPrice(price);
			master.submit(t);
		}
		long start = System.currentTimeMillis();
		System.out.println("expected result: " + expectedTotalPrice + ", 开始时间: " + start + " milliseconds");
		master.execute();
		while (true) {
			if (master.isComplete()) {
				int priceResult = master.getResult();
				long lasts = System.currentTimeMillis() - start;
				System.out.println("final result: " + priceResult + ", 执行时间: " + lasts + " milliseconds");
				break;
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
