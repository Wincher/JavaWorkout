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
    //return an uncompleted CompletableFuture
    public static CompletableFuture<Integer> compute() {
		return new CompletableFuture<>();
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();

        //complete 只会首次生效，后续调用无效。
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        //移动这段代码位置可证明,complete 只会首次生效，后续调用无效。
        f.complete(33);

        //obtrude can force update the returning value of CompletableFuture
        System.out.println(Thread.currentThread().getName() + " f.get(): " + f.get());
        f.obtrudeValue(99);
        System.out.println("after f.obtrudeValue(99)..");
        System.out.println(Thread.currentThread().getName() + " f.get(): " + f.get());

        f.completeExceptionally(new Exception());
        System.out.println("after f.completeExceptionally(new Exception())..");
        System.out.println(Thread.currentThread().getName() + " f.get(): " + f.get());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("nothing happen? wait 1s and invoke obtrudeException()");
        f.obtrudeException(new Exception());
        System.out.println(Thread.currentThread().getName() + ": " + f.get());
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
