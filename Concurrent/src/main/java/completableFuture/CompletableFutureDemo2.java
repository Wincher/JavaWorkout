package completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author wincher
 * <p> completableFuture <p>
 */
public class CompletableFutureDemo2 {
    private static final Random rand = new Random();
    private static final long t = System.currentTimeMillis();

    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return rand.nextInt(1000);
    }
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(CompletableFutureDemo2::getMoreData);
        //如果主线程需要结果，应直接用 future.get()，避免多余的 whenComplete,如需进行操作eg: thenApply(),
        Future<Integer> f = future.whenComplete((v, e) -> {
            if (e != null)
                System.err.println(e.getMessage());
            else
                System.out.println("future get value:" + v);
        }).thenApply(v -> v * 2);
        System.out.println("f.get(): " + f.get());
    }
}
