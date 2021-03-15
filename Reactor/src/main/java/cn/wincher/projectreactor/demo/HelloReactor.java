package cn.wincher.projectreactor.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author wincher
 * <p> PACKAGE_NAME <p>
 */
public class HelloReactor {
    public static void main(String[] args) {
        Flux.just(1,2,3,4).subscribe(i -> {System.out.println(Thread.currentThread().getName() + i );});
        Mono.just(0);

        Flux.fromArray(new String[]{"a","b","c"});
        Flux.fromStream(new ArrayList<>().stream());

        Flux.error(new Exception("error occur!")).subscribe(
            System.out::println,
            System.err::println,
            () -> System.out.println("Completed!")
        );

        Flux.just(1,3,3,3).subscribe(
            System.out::println,
            System.err::println,
            () -> System.out.println("Completed!")
        );

        Mono<Date> m1 = Mono.just(new Date());
        Mono<Date> m2 = Mono.defer(()->Mono.just(new Date()));
        m1.subscribe(System.out::println);
        m2.subscribe(System.out::println);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m1.subscribe(System.out::println);
        m2.subscribe(System.out::println);
    }

}
