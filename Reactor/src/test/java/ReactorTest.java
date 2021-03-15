import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class ReactorTest {

    @Test
    public void reactorZipTest() {
        Flux<String> stringFlux = Flux.just("a", "b","c", "d", "e");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.zip(stringFlux, Flux.interval(Duration.ofSeconds(1)))
            .subscribe(t -> System.out.println(t.getT1()), System.err::println, countDownLatch::countDown);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void asyncReactorTest() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> syncMethod())
            .subscribeOn(Schedulers.elastic())
            .subscribe(System.out::println, null, countDownLatch::countDown);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customizeSubscriber() {
        Flux.just(1,3,3,4,5,352,32,35,235,2,35,23,52,35,235,23,5,23)
            .doOnRequest(s -> System.out.println("no. " + s))
            .subscribe(new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    System.out.println("Subscription start...");
                    request(2);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    System.out.println("Received " + value);
                    request(2);
                }
            });
    }

    private Integer syncMethod() {
        Integer.valueOf(1);
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 333;
    }
    private Integer syncMethod2() {
        try {
            TimeUnit.SECONDS.sleep(33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 333;
    }
}
