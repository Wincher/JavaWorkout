package test;

import java.util.concurrent.*;

/**
 * @author wincher
 * <p> test <p>
 */
public class TestCompletableFutureTimeOut {
    public static void main(String[] args) throws InterruptedException {
        Executor asyncPool = ForkJoinPool.commonPool();
        try {
            CompletableFuture.supplyAsync(() -> {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) break;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("while..." + Thread.currentThread().isInterrupted() );
                }
                return null;
            }).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        TimeUnit.SECONDS.sleep(200);
    }

}
