import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {
    @Test
    public void test1() throws InterruptedException {
        CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName());
        });
        Thread.sleep(400000);
    }
}
