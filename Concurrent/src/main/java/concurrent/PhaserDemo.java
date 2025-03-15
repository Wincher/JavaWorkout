package concurrent;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserDemo {
	public static void main(String[] args) {
		RacePhaser phaser = new RacePhaser();
		Racing[] racings = new Racing[5];
		for (int i = 0; i < racings.length; i++) {
			racings[i] = new Racing(phaser);
			phaser.register();
		}

		Thread[] threads = new Thread[racings.length];
		for (int i = 0; i < racings.length; i++) {
			threads[i] = new Thread(racings[i], "Car"+i);
			threads[i].start();
		}
		for (int i = 0; i < racings.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//phaser于0,isTerminated为true
		System.out.println(Thread.currentThread().getName() + "Phaser:" + phaser.getPhase() +  " Phaser has finished:" + phaser.isTerminated());
	}
}

class RacePhaser extends Phaser {
	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		switch (phase) {
			case 0:
				return ready();
			case 1:
				return stage1();
			case 2:
				return stage2();
			default:
				return true;
		}
	}

	private boolean stage2() {
		System.out.println(Thread.currentThread().getName() + ": onAdvance stage2 finished");
		return true;
	}

	private boolean stage1() {
		System.out.println(Thread.currentThread().getName() + ": onAdvance stage1 finished");
		return false;
	}

	private boolean ready() {
		System.out.println(Thread.currentThread().getName() + ": onAdvance is Ready");
		return false;
	}
}

class Racing implements Runnable {

	private final Phaser phaser;

	Racing(Phaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + ": starting point...");
		ready();
		int i = phaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName() + ": returned phaser: " + i);
		doRacing(1);
		i = phaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName() + ": returned phaser: " + i);
		doRacing(2);
		i = phaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName() + ": returned phaser: " + i);

	}

	private void doRacing(int stage) {
		System.out.println(Thread.currentThread().getName() + ": starting stage " + stage);
		long duration = (long) (Math.random() * 1000);
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": end stage " + stage);
	}

	private void ready() {
		long duration = (long) (Math.random() * 10);
		try {
			TimeUnit.MILLISECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ":ready");
	}
}
