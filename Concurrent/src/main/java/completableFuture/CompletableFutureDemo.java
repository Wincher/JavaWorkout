package completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * <p> completableFuture <p>
 */
public class CompletableFutureDemo {
    public static CompletableFuture<Integer> compute() {
		return new CompletableFuture<>();
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();

        new Client("Client1", f).start();
        new Client("Client2", f).start();
        f.complete(33);

        System.out.println("waiting");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + ": " + f.get());
        //obtrude can force update the returning value of CompletableFuture
        f.complete(44);
        System.out.println(Thread.currentThread().getName() + ": " + f.get());
        System.out.println("waiting");
        TimeUnit.SECONDS.sleep(1);
        f.obtrudeValue(99);
        System.out.println(Thread.currentThread().getName() + ": " + f.get());

        f.completeExceptionally(new Exception());
        System.out.println(Thread.currentThread().getName() + ": " + f.get());
        System.out.println("nothing happen? waiting.. and invoke obtrudeException()");
        TimeUnit.SECONDS.sleep(1);
        f.obtrudeException(new Exception());
        System.out.println(Thread.currentThread().getName() + ": " + f.get());
        System.in.read();
    }

    static class Client extends Thread {
        final CompletableFuture<Integer> cf;
        Client(String threadName, CompletableFuture<Integer> cf) {
            super(threadName);
            this.cf = cf;
        }
        @Override
        public void run() {
            try {
                cf.complete(new Random().nextInt(1000));
                System.out.println(this.getName() + ": " + cf.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
