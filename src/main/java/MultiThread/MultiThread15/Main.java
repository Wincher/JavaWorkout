package MultiThread.MultiThread15;

import java.util.Random;

/**
 * Created by wincher on 31/08/2017.
 * Master将task分给多个worker执行大幅减少时间
 */
public class Main {
	
	public static void main(String[] args) {
		Master master = new Master(new Worker() , 10);
		
		Random r = new Random();
		for (int i = 1; i <= 100; i++) {
			Task t = new Task();
			t.setId(i);
			t.setPrice(r.nextInt(1000));
			master.submit(t);
		}
		master.execute();
		
		long start = System.currentTimeMillis();
		
		while (true) {
			if (master.isComplete()) {
				long lasts = System.currentTimeMillis() - start;
				int priceResult = master.getResult();
				System.out.println("final result: " + priceResult + ", 执行时间: " + lasts);
				break;
			}
		}
	}
	
}
